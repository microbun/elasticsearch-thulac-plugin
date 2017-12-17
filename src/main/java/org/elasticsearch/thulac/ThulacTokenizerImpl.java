package org.elasticsearch.thulac;

import org.thunlp.thulac.data.POCGraph;
import org.thunlp.thulac.data.TaggedWord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizerImpl implements Iterator<Token> {
    private static Iterator<Token> empty = new ArrayList<Token>(0).iterator();

    private static Thulac thulac = Thulac.getInstance();

    private Iterator<Token> tokens;

    @Override
    public boolean hasNext() {
        return tokens.hasNext();
    }

    @Override
    public Token next() {
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
            raw = bdr.toString().trim();
            POCGraph graph = new POCGraph();
            ArrayList<TaggedWord> words = new ArrayList<>();
            thulac.segment(raw, graph, words);
            if (!words.isEmpty()) {
                int length = 0, offset = 0;
                ArrayList<Token> tokenArray = new ArrayList<>(words.size());
                for (TaggedWord token : words) {
                    length = token.word.length();
                    tokenArray.add(new Token(token.word, offset, offset + length, length));
                    offset += length;
                }
                tokens = tokenArray.iterator();
            } else {
                tokens = empty;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
