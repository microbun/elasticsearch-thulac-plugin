package org.elasticsearch.thulac;

/**
 * Created by micro on 2017-12-17.
 */
public class Token {

    private String word;

    private int length;

    private int startOffset;

    private int endOffset;

    public Token(String word, int startOffset, int endOffset,int length) {
        this.word = word;
        this.length  = length;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public String getWord() {
        return word;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public int getLength() {
        return length;
    }
}
