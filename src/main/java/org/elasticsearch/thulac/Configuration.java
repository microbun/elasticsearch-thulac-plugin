package org.elasticsearch.thulac;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created by micro on 2017-12-17.
 */
public class Configuration {

    private static Configuration instance;

    public Configuration() {

    }

    String userDict = null;
    boolean useT2S = true;
    boolean segOnly = true;
    boolean useFilter =  false;
    Path modelPath = FileSystems.getDefault().getPath("models");

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public void configuration(Environment environment, Settings settings) {
        userDict = settings.get("userDict", null);
        useT2S = Boolean.parseBoolean(settings.get("useT2S", "false"));
//        segOnly = Boolean.parseBoolean(settings.get("segOnly", "false"));
        useFilter = Boolean.parseBoolean(settings.get("useFilter", "false"));
        modelPath = environment.pluginsFile().resolve("org/thulac/models");

    }
}
