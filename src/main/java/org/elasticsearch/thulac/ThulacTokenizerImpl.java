package org.elasticsearch.thulac;


import org.thulac.base.POCGraph;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizerImpl implements Iterator<Token> {
    private static Iterator<Token> empty = new ArrayList<Token>(0).iterator();

    private static Thulac thulac;

    ThulacTokenizerImpl(){
        if (thulac==null){
            thulac = Thulac.getInstance();
        }
    }

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
            ArrayList<String> words = new ArrayList<>();
            thulac.segment(raw, graph, words);
            if (!words.isEmpty()) {
                int length = 0, offset = 0;
                ArrayList<Token> tokenArray = new ArrayList<>(words.size());
                for (String token : words) {
                    length = token.length();
                    tokenArray.add(new Token(token, offset, offset + length, ""));
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
