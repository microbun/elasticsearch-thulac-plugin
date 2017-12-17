package org.elasticsearch.thulac;


import org.thulac.base.POCGraph;
import org.thulac.base.SegmentedSentence;
import org.thulac.base.TaggedSentence;
import org.thulac.character.CBTaggingDecoder;
import org.thulac.manage.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by micro on 2017-12-17.
 */
public class Thulac {
    private static Thulac thulac;
    //    private List<IPreprocessPass> preProcess = new ArrayList<>();
//    private List<IPostprocessPass> postProcess = new ArrayList<>();
    private CBTaggingDecoder taggingDecoder = new CBTaggingDecoder();


    private CBTaggingDecoder cbTaggingDecoder = new CBTaggingDecoder();
    Preprocesser preprocesser;
    Postprocesser nsDict;
    Postprocesser idiomDict;
    Postprocesser userDict;
    Punctuation punctuation;
    TimeWord timeword;
    NegWord negword;
    VerbWord verbword;
    Filter filter;

    Configuration configuration = Configuration.getInstance();

    private String join(Path path, String... more) {
        return Paths.get(path.toAbsolutePath().toString(), more).toAbsolutePath().toString();
    }

    private Thulac() {
        init();
    }

    public void init(){
        String prefix = configuration.segOnly ? "cws_" : "model_c_";
        Character separator = '_';
        cbTaggingDecoder.separator = separator;
        try {
            if (configuration.segOnly) {
                cbTaggingDecoder.threshold = 0;

            } else {
                cbTaggingDecoder.threshold = 10000;
            }
            cbTaggingDecoder.init(
                    join(configuration.modelPath, prefix + "model.bin"),
                    join(configuration.modelPath, prefix + "dat.bin"),
                    join(configuration.modelPath, prefix + "label.txt")
            );
            cbTaggingDecoder.setLabelTrans();

            preprocesser = new Preprocesser();
            preprocesser.setT2SMap(join(configuration.modelPath, "t2s.dat"));
            nsDict = new Postprocesser(join(configuration.modelPath,"ns.dat"), "ns", false);
            idiomDict = new Postprocesser(join(configuration.modelPath, "idiom.dat"), "i", false);
            userDict = null;
            if (configuration.userDict != null) {
                userDict = new Postprocesser(configuration.userDict, "uw", true);
            }
            if (configuration.useFilter) {
                filter = new Filter(join(configuration.modelPath,"xu.dat"), join(configuration.modelPath,"time.dat"));

            }
            punctuation = new Punctuation(join(configuration.modelPath,  "singlepun.dat"));
            timeword = new TimeWord();
            negword = new NegWord(join(configuration.modelPath, "neg.dat"));
            verbword = new VerbWord(join(configuration.modelPath,"vM.dat"), join(configuration.modelPath,  "vD.dat"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean segment(String raw, POCGraph graph, List<String> words) {
        SegmentedSentence segged = new SegmentedSentence();
        TaggedSentence tagged = new TaggedSentence();
        if (configuration.useT2S) {
            String traw = new String();
            raw = preprocesser.T2S(preprocesser.clean(raw, graph));
        } else {
            raw = preprocesser.clean(raw, graph);
        }
        cbTaggingDecoder.segment(raw, graph, tagged);

        cbTaggingDecoder.get_seg_result(segged);
        nsDict.adjust(segged);
        idiomDict.adjust(segged);
        punctuation.adjust(segged);
        timeword.adjust(segged);
        negword.adjust(segged);
        if (userDict != null) {
            userDict.adjust(segged);
        }
        if (configuration.useFilter) {
            filter.adjust(segged);
        }
        for(int j=0;j<segged.size();j++) {
            words.add(segged.get(j));
        }

        return true;
    }


    public static Thulac getInstance() {
        if (thulac == null) {
            thulac = new Thulac();
        }
        return thulac;
    }


}
