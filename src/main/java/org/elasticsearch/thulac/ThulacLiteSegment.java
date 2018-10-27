package org.elasticsearch.thulac;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.thulac.postprocess.*;
import org.elasticsearch.thulac.preprocess.ConvertT2SPassBuilder;
import org.elasticsearch.thulac.preprocess.PreProcessPassBuilder;
import org.thunlp.thulac.cb.CBTaggingDecoder;
import org.thunlp.thulac.data.POCGraph;
import org.thunlp.thulac.data.TaggedWord;
import org.thunlp.thulac.postprocess.IPostprocessPass;
import org.thunlp.thulac.preprocess.IPreprocessPass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThulacLiteSegment {

    private static final Map<String, CBTaggingDecoder> decoder = new HashMap<>();

    private static final Map<Configuration, ThulacLiteSegment> cache = new ConcurrentHashMap<>();
    private Logger logger;
    private CBTaggingDecoder taggingDecoder;
    // preprocess passes
    private List<IPreprocessPass> pre = new ArrayList<>();
    // postprocess passes
    private List<IPostprocessPass> post = new ArrayList<>();

    private ThulacLiteSegment(Configuration configuration) throws IOException {
        logger = Loggers.getLogger(getClass(), configuration.getSettings());
        synchronized (decoder) {
            init(configuration);
        }
    }

    public static ThulacLiteSegment getInstance(Configuration configuration) throws IOException {
        ThulacLiteSegment segment;
        if (cache.containsKey(configuration)) {
            segment = cache.get(configuration);
        } else {
            segment = new ThulacLiteSegment(configuration);
            cache.put(configuration, segment);
        }
        return segment;
    }

    private void init(Configuration configuration) throws IOException {
        // segmentation
        // load model
        String prefix = configuration.segOnly ? "cws_" : "model_c_";
        if (!decoder.containsKey(prefix)) {
            CBTaggingDecoder temp = new CBTaggingDecoder();
            temp.threshold = configuration.segOnly ? 0 : 10000;
            temp.loadFiles(
                    join(configuration.modelPath, prefix + "model.bin"),
                    join(configuration.modelPath, prefix + "dat.bin"),
                    join(configuration.modelPath, prefix + "label.txt"));
            temp.setLabelTrans();
            decoder.put(prefix, temp);
        }
        taggingDecoder = decoder.get(prefix);

        //pre pass
        pre.add(PreProcessPassBuilder.getInstance());
        if (configuration.t2s) {
            pre.add(ConvertT2SPassBuilder.getInstance(join(configuration.modelPath, "t2s.dat")));
        }

        //post pass
        post.add(DictionaryPassBuilder.getInstance(join(configuration.modelPath, "ns.dat"), "ns", false));
        post.add(DictionaryPassBuilder.getInstance(join(configuration.modelPath, "idiom.dat"), "i", false));
        post.add(DictionaryPassBuilder.getInstance(join(configuration.modelPath, "singlepun.dat"), "w", false));
        post.add(TimeWordPassBuilder.getInstance());
        post.add(DoubleWordPassBuilder.getInstance());
        post.add(SpecialPassBuilder.getInstance());
        post.add(NegWordPassBuilder.getInstance(join(configuration.modelPath, "neg.dat")));
        if (configuration.userDict != null) {
            Path dictPath =Paths.get(join(configuration.modelPath, configuration.userDict));
            if (Files.exists(dictPath)) {
                post.add(DictionaryPassBuilder.getInstance(dictPath.toAbsolutePath().toString(), "uw", true));
            } else {
                logger.warn("not found user_dict:{}", configuration.userDict);
                dictPath = Paths.get(configuration.userDict);
                if (Files.exists(dictPath)) {
                    post.add(DictionaryPassBuilder.getInstance(dictPath.toAbsolutePath().toString(), "uw", true));
                } else {
                    logger.warn("not found user_dict:{}", configuration.userDict);
                }
            }
        }
        if (configuration.filter) {
            post.add(FilterPassBuilder.getInstance(join(configuration.modelPath, "xu.dat"), join(configuration.modelPath, "time.dat")));
        }

    }


    private String join(Path path, String... more) {
        return Paths.get(path.toAbsolutePath().toString(), more).toAbsolutePath().toString();
    }

    public List<TaggedWord> segment(String raw) {
        List<TaggedWord> words = new ArrayList<>();
        POCGraph graph = new POCGraph();
        for (IPreprocessPass pass : pre) {
            raw = pass.process(raw, graph);
        }
        taggingDecoder.segment(raw, graph, words);
        for (IPostprocessPass pass : post) {
            pass.process(words);
        }
        return words;
    }
}
