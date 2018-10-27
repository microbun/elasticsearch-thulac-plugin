package org.elasticsearch.thulac;

import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by micro on 2017-12-17.
 */
public class Configuration {

    String userDict = "userdict.txt";
    boolean t2s = false;
    boolean segOnly = true;
    boolean filter = false;
    Path modelPath = FileSystems.getDefault().getPath("models/");
    private Environment environment;
    private IndexSettings indexSettings;
    private Settings settings;
    private Logger logger = ESLoggerFactory.getLogger(getClass());

    public Configuration() {
    }

    public Configuration(Environment environment, IndexSettings indexSettings, Settings settings) {
        this.environment = environment;
        this.indexSettings = indexSettings;
        this.settings = settings;
        userDict = settings.get("user_dict", "userdict.txt");
        t2s = settings.getAsBoolean("t2s", true);
//        segOnly = settings.getAsBoolean("seg_only", true);
        filter = settings.getAsBoolean("filter", false);
        modelPath = environment.pluginsFile().resolve("thulac/models");
//        logger.info("thulac settings: path={}", modelPath.toAbsolutePath().toString());
//        logger.info("thulac settings: user_dict={} use_t2s={} seg_only={} use_filter={} ", userDict, segOnly, useFilter);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public IndexSettings getIndexSettings() {
        return indexSettings;
    }

    public Settings getSettings() {
        return settings;
    }
}
