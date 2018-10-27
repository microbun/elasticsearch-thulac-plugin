package org.elasticsearch.thulac.preprocess;

import org.thunlp.thulac.preprocess.PreProcessPass;

public class PreProcessPassBuilder {

    private static PreProcessPass instance = new PreProcessPass();

    public static PreProcessPass getInstance() {
        return instance;
    }


}
