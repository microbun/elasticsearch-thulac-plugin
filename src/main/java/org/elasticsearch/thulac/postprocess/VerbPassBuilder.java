package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.VerbPass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VerbPassBuilder {
    private static Map<String, VerbPass> cache = new ConcurrentHashMap<>();

    public static VerbPass getInstance(String vMFile, String vDFile) throws IOException {
        String key = vMFile + "#" + vDFile;
        if (!cache.containsKey(key)) {
            cache.put(key, new VerbPass(vMFile, vDFile));
        }
        return cache.get(key);
    }
}
