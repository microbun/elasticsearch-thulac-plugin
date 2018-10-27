package org.elasticsearch.index;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.thulac.Configuration;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacAnalyzer extends Analyzer {

    private Configuration configuration;

    public ThulacAnalyzer(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return new TokenStreamComponents(new ThulacTokenizer(configuration));
    }

}
