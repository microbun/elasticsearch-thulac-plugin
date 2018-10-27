package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.TimeWordPass;

public class TimeWordPassBuilder {
    private static TimeWordPass instance = new TimeWordPass();

    public static TimeWordPass getInstance() {
        return instance;
    }
}
