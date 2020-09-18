import org.elasticsearch.thulac.Configuration;
import org.elasticsearch.thulac.ThulacLiteTokenizerScanner;
import org.junit.Test;
import org.thunlp.thulac.data.TaggedWord;

import java.io.*;

/**
 * Created by micro on 2017-12-17.
 */
public class TestThulac {

    @Test
    public void test2() throws IOException {
        ThulacLiteTokenizerScanner tokenizer = new ThulacLiteTokenizerScanner(new Configuration());
        InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResource("input").openStream());
        tokenizer.reset(isr);
        while (tokenizer.hasNext()) {
            TaggedWord token = tokenizer.next();
            System.out.println("word = " + token.word + ", tag= " + token.tag + ", start= " + token.startOffset + ", end=" + token.endOffset);
        }
    }

}
