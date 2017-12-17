package org.elasticsearch.plugin.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.ThaiTokenizerFactory;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.thulac.ThulacAnalyzerProvider;
import org.elasticsearch.thulac.ThulacTokenizerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Microbun on 2017/12/17.
 */
public class ThulacAnalysisPlugin  extends Plugin implements AnalysisPlugin {

  public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
    Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();
    extra.put("thulac", ThulacTokenizerFactory::getThulacIndexTokenizerFactory);
    return extra;
  }

  public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
    Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
    extra.put("thulac", ThulacAnalyzerProvider::getThulacAnalyzerProvider);
    return extra;
  }
}
