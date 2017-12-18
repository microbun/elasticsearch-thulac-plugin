import org.elasticsearch.thulac.ThulacTokenizerImpl;
import org.elasticsearch.thulac.Token;
import org.junit.Test;

import java.io.StringReader;

/**
 * Created by micro on 2017-12-17.
 */
public class TestThulac {
//
//    @Test
//    public  void test() throws IOException {
////        Thulac thulac = new Thulac();
////        String modelDir = FileSystems.getDefault().getPath("models").toAbsolutePath().toString()+"\\";
////        char separator = 95;
////        String userDict = null;
////        boolean useT2S = true;
////        boolean segOnly = true;
////        boolean useFilter = false;
////        StringInputProvider input = new StringInputProvider("2016年12月20日,發放，怎麼看是繁體到簡體呢");
////        StringOutputHandler output  =new StringOutputHandler();
////
////        Thulac.split(modelDir, separator, userDict, useT2S, segOnly, useFilter, input, output);
////        System.out.println(output.getString());
//
//
//
//    }

    @Test
    public void test2() {
        ThulacTokenizerImpl scaner = new ThulacTokenizerImpl();
        scaner.reset(new StringReader("2016年12月20日,我可以發放吗？，怎麼看是繁體到簡體呢,好吃好吃，http://www.baidu.com"));
        while (scaner.hasNext()) {
            Token token = scaner.next();
            System.out.println("word = " + token.getWord() + ", tag= " + token.getTag() + ", start= " + token.getStartOffset() + ", end=" + token.getEndOffset());
        }
    }
}
