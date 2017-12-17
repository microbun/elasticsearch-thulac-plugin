package org.elasticsearch.thulac;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.AnalyzerProvider;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacAnalyzerProvider  extends AbstractIndexAnalyzerProvider<ThulacAnalyzer> {

    private ThulacAnalyzer thulacAnalyzer;


    /**
     * Constructs a new analyzer component, with the index name and its settings and the analyzer name.
     *
     * @param indexSettings the settings and the name of the index
     * @param name          The analyzer name
     * @param settings
     */
    public ThulacAnalyzerProvider(IndexSettings indexSettings,Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
        Configuration.getInstance().configuration(environment,settings);
        thulacAnalyzer = new ThulacAnalyzer();
    }

    @Override
    public ThulacAnalyzer get() {
        return thulacAnalyzer;
    }

    public static AnalyzerProvider<? extends Analyzer> getThulacAnalyzerProvider(IndexSettings indexSettings,
                                                                                     Environment environment,
                                                                                     String name,
                                                                                     Settings settings) {
        ThulacAnalyzerProvider analyzerProvider = new ThulacAnalyzerProvider(indexSettings,
                environment,
                name,
                settings);

        return analyzerProvider;
    }
}
