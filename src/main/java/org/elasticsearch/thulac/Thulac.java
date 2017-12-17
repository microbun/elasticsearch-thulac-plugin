package org.elasticsearch.thulac;

import org.thunlp.thulac.cb.CBTaggingDecoder;
import org.thunlp.thulac.data.POCGraph;
import org.thunlp.thulac.data.TaggedWord;
import org.thunlp.thulac.postprocess.*;
import org.thunlp.thulac.preprocess.ConvertT2SPass;
import org.thunlp.thulac.preprocess.IPreprocessPass;
import org.thunlp.thulac.preprocess.PreprocessPass;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by micro on 2017-12-17.
 */
public class Thulac {
    private static Thulac thulac;
    private List<IPreprocessPass> preProcess = new ArrayList<>();
    private List<IPostprocessPass> postProcess = new ArrayList<>();
    private CBTaggingDecoder taggingDecoder = new CBTaggingDecoder();

    Configuration configuration = Configuration.getInstance();

    private String join(Path path, String... more) {
        return Paths.get(path.toAbsolutePath().toString(), more).toAbsolutePath().toString();
    }

    private Thulac() {
        taggingDecoder.threshold = configuration.segOnly ? 0 : 10000;
        String prefix = configuration.segOnly ? "cws_" : "model_c_";

        try {
            taggingDecoder.loadFiles(
                    join(configuration.modelPath, prefix + "model.bin"),
                    join(configuration.modelPath, prefix + "dat.bin"),
                    join(configuration.modelPath, prefix + "label.txt"));
            taggingDecoder.setLabelTrans();

            preProcess.add(new PreprocessPass());

            if (configuration.useT2S) preProcess.add(new ConvertT2SPass(join(configuration.modelPath, "t2s.dat")));

            postProcess.add(new DictionaryPass(join(configuration.modelPath, "ns.dat"), "ns", false));
            postProcess.add(new DictionaryPass(join(configuration.modelPath, "idiom.dat"), "i", false));
            postProcess.add(new DictionaryPass(join(configuration.modelPath, "singlepun.dat"), "w", false));
            postProcess.add(new TimeWordPass());
            postProcess.add(new DoubleWordPass());
            postProcess.add(new SpecialPass());
            postProcess.add(new NegWordPass(join(configuration.modelPath, "neg.dat")));
            if (configuration.userDict != null) postProcess.add(new DictionaryPass(configuration.userDict, "uw", true));
            if (configuration.useFilter) {
                postProcess.add(new FilterPass(join(configuration.modelPath, "xu.dat"), join(configuration.modelPath, "time.dat")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean segment(String raw, POCGraph graph, List<TaggedWord> words) {
        for (IPreprocessPass pass : preProcess) raw = pass.process(raw, graph);
        boolean r = taggingDecoder.segment(raw, graph, words);
        for (IPostprocessPass pass : postProcess) pass.process(words);
        return r;
    }

    public static Thulac getInstance() {
        if (thulac == null) {
            thulac = new Thulac();
        }
        return thulac;
    }


}
