package com.zhuj.code;

import com.zhuj.code.lang.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class AppMain {
    public static Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {

        // Reflections.logObjectField(new Mt(), "ss","sss", "ssss");
        String[] strings = new String[]{"adaf", "add"};
        strings = null;
        int line = 0;
        for (; line != 1; line++) {
            logger.debug("line = " + line);
        }
        logger.debug("line2 = " + line);
    }

    static class Mt {
        String ss = "wwwwwwwwwww";
        private String sss = "wwwwwwwwwww";
        protected String ssss = "wwwwwwwwwww";
    }
}