package org.elasticsearch.index;

import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.thulac.Configuration;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizerFactory extends AbstractTokenizerFactory {

    private Configuration configuration;

    public ThulacTokenizerFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, settings, name);
        configuration = new Configuration(environment, indexSettings, settings);
    }

    @Override
    public Tokenizer create() {
        return new ThulacTokenizer(configuration);
    }
}
