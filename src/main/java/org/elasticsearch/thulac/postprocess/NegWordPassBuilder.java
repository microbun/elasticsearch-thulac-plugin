package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.NegWordPass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NegWordPassBuilder {
    private static Map<String, NegWordPass> cache = new ConcurrentHashMap<>();

    public static NegWordPass getInstance(String negDatFile) throws IOException {
        String key = negDatFile;
        if (!cache.containsKey(key)) {
            cache.put(key, new NegWordPass(negDatFile));
        }
        return cache.get(key);
    }
}
