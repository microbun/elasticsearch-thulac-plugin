package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.FilterPass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FilterPassBuilder {

    private static Map<String, FilterPass> cache = new ConcurrentHashMap<>();

    public static FilterPass getInstance(String xuDatFile, String timeDatFile) throws IOException {
        String key = xuDatFile + "#" + timeDatFile;
        if (!cache.containsKey(key)) {
            cache.put(key, new FilterPass(xuDatFile, timeDatFile));
        }
        return cache.get(key);
    }
}
