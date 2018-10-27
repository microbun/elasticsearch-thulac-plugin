package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.SpecialPass;

public class SpecialPassBuilder {
    private static SpecialPass instance = new SpecialPass();

    public static SpecialPass getInstance() {
        return instance;
    }
}
