package org.elasticsearch.index;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.thulac.Configuration;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacAnalyzerProvider extends AbstractIndexAnalyzerProvider<ThulacAnalyzer> {

    private ThulacAnalyzer thulacAnalyzer;

    /**
     * Constructs a new analyzer component, with the index name and its settings and the analyzer name.
     *
     * @param indexSettings the settings and the name of the index
     * @param name          The analyzer name
     * @param settings
     */
    public ThulacAnalyzerProvider(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
        Configuration configuration = new Configuration(environment,indexSettings, settings);
        thulacAnalyzer = new ThulacAnalyzer(configuration);
    }

    @Override
    public ThulacAnalyzer get() {
        return thulacAnalyzer;
    }

}
