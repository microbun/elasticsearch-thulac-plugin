package org.elasticsearch.index;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.elasticsearch.thulac.Configuration;
import org.elasticsearch.thulac.ThulacLiteTokenizerScanner;
import org.thunlp.thulac.data.TaggedWord;

import java.io.IOException;

/**
 * Created by micro on 2017-12-17.
 */
public class ThulacTokenizer extends Tokenizer {

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    //    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);
    private ThulacLiteTokenizerScanner scanner;
    private int endPosition;


    public ThulacTokenizer(Configuration configuration) {
        try {
            scanner = new ThulacLiteTokenizerScanner(configuration);
        } catch (IOException e) {
            throw new IllegalArgumentException("thulac configuration error", e);
        }
    }

    @Override
    public boolean incrementToken() {
        clearAttributes();
        if (scanner.hasNext()) {
            TaggedWord token = scanner.next();
            termAtt.append(token.word);
            termAtt.setLength(token.word.length());
            offsetAtt.setOffset(token.startOffset, token.endOffset);
            endPosition = token.endOffset;
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
