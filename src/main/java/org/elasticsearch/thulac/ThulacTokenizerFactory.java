package org.elasticsearch.thulac;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizerFactory extends AbstractTokenizerFactory {

    public ThulacTokenizerFactory(IndexSettings indexSettings,Environment environment, String ignored, Settings settings) {
        super(indexSettings, ignored, settings);
        Configuration.getInstance().configuration(environment,settings);
    }

    public static TokenizerFactory getThulacIndexTokenizerFactory(IndexSettings indexSettings,
                                                                 Environment environment,
                                                                 String name,
                                                                 Settings settings) {
        ThulacTokenizerFactory tokenizerFactory = new ThulacTokenizerFactory(indexSettings,
                environment,
                name,
                settings);
        return tokenizerFactory;
    }


    @Override
    public Tokenizer create() {
        return new ThulacTokenizer();
    }
}
