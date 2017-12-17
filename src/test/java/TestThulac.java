import org.elasticsearch.thulac.ThulacTokenizerImpl;
import org.elasticsearch.thulac.Token;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by micro on 2017-12-17.
 */
public class TestThulac {

    @Test
    public  void test() throws IOException {
//        Thulac thulac = new Thulac();
//        String modelDir =this.getClass().getResource("models").getPath()+"/";
//        char separator = 95;
//        String userDict = null;
//        boolean useT2S = false;
//        boolean segOnly = false;
//        boolean useFilter = false;
//        StringInputProvider input = new StringInputProvider("我是中国人");
//        StringOutputHandler output  =new StringOutputHandler();
//
//        Thulac.split(modelDir, separator, userDict, useT2S, segOnly, useFilter, input, output);
//        System.out.println(output.getString());

         ThulacTokenizerImpl scaner =  new ThulacTokenizerImpl();
         scaner.reset(new StringReader("正在准备要安装的文件在2017年10月20日，我的编号10115"));
         while (scaner.hasNext()){
             Token token  = scaner.next();
             System.out.println(token.getWord());
         }

    }
}
