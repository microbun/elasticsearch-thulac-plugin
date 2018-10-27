package org.elasticsearch.thulac.postprocess;

import org.thunlp.thulac.postprocess.DictionaryPass;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DictionaryPassBuilder {

    private static Map<String, DictionaryPass> cache = new ConcurrentHashMap<>();

    public static DictionaryPass getInstance(String dictFile, String tag, boolean isTxt) throws IOException {
        String key = dictFile + "#" + tag + "#" + isTxt;
        if (!cache.containsKey(key)) {
            cache.put(key, new DictionaryPass(dictFile, tag, isTxt));
        }
        return cache.get(key);
    }
}
