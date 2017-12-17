package org.elasticsearch.thulac;

import org.apache.lucene.analysis.Analyzer;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacAnalyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return new TokenStreamComponents(new ThulacTokenizer());
    }

}
