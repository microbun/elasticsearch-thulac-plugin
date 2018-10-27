package org.elasticsearch.thulac;


import org.thunlp.thulac.data.TaggedWord;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacLiteTokenizerScanner implements Iterator<TaggedWord> {


    //    private Logger logger;
    private ThulacLiteSegment segment;
    private Iterator<TaggedWord> tokens;

    public ThulacLiteTokenizerScanner(Configuration configuration) throws IOException {
//        logger = Loggers.getLogger(getClass(), configuration.getSettings());
        segment = ThulacLiteSegment.getInstance(configuration);
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNext();
    }

    @Override
    public TaggedWord next() {
        return tokens.next();
    }

    @Override
    public void remove() {
        tokens.remove();
    }

    public void reset(Reader reader) {
        String raw;
        try {
            StringBuilder bdr = new StringBuilder();
            int size = 1024;
            char[] buf = new char[size];
            while ((size = reader.read(buf, 0, size)) != -1) {
                bdr.append(new String(buf, 0, size));
            }
            raw = bdr.toString();
            List<TaggedWord> words = segment.segment(raw);
            tokens = words.iterator();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
