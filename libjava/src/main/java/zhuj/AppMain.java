package zhuj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMain {
    public static Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {

        String s = " XXXXXXXXXXXXX ";
        logger.info(s);

    }

    static class Mt {
        String defaultStr = "default field";
        private String privateStr = "private field";
        protected String protectedStr = "protected field";
        public String publicStr = "public field";
    }
}