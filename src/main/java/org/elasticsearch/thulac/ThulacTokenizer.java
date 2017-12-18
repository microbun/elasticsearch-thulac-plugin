package org.elasticsearch.thulac;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizer extends Tokenizer {

    private ThulacTokenizerImpl scanner;


    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
//    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);

    private int endPosition;


    public ThulacTokenizer(){
        scanner = new ThulacTokenizerImpl();
    }

    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        if (scanner.hasNext()) {
            Token token = scanner.next();
            termAtt.append(token.getWord());
            termAtt.setLength(token.getWord().length());
            offsetAtt.setOffset(token.getStartOffset(), token.getEndOffset());
//            typeAtt.setType("<"+token.getTag()+">");
            endPosition = token.getEndOffset();
            return true;
        }
        return false;
    }


    @Override
    public final void end() throws IOException {
        super.end();
        int finalOffset = correctOffset(this.endPosition);
        offsetAtt.setOffset(finalOffset, finalOffset);
    }

//    @Override
//    public void close() throws IOException {
//        super.close();
//        scanner.reset(input);
//    }

    @Override
    public void reset() throws IOException {
        super.reset();
        scanner.reset(input);
    }


}
