package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.DoubleWordPass;

public class DoubleWordPassBuilder {

    private static DoubleWordPass instance = new DoubleWordPass();

    public static DoubleWordPass getInstance() {
        return instance;
    }
}
