package org.elasticsearch.thulac;

/**
 * Created by micro on 2017-12-17.
 */
public class Token {

    private String word;

    private String tag;

    private int startOffset;

    private int endOffset;

    public Token(String word, int startOffset, int endOffset,String tag) {
        this.word = word;
        this.tag  = tag;
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

    public String getTag() {
        return tag;
    }
}
