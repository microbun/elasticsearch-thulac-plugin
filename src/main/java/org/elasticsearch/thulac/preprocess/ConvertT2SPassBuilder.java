package org.elasticsearch.thulac.preprocess;

import org.thunlp.thulac.preprocess.ConvertT2SPass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConvertT2SPassBuilder {

    private static Map<String, ConvertT2SPass> cache = new ConcurrentHashMap<>();

    public static ConvertT2SPass getInstance(String file) throws IOException {
        String key = file;
        if (!cache.containsKey(key)) {
            cache.put(key, new ConvertT2SPass(file));
        }
        return cache.get(key);
    }
}
