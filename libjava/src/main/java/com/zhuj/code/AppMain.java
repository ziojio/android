package com.zhuj.code;

import com.zhuj.code.lang.Reflections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMain {
    static Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {

        Reflections.logObjectField(new Mt(), "ss","sss", "ssss");
    }

    static class Mt {
        String ss = "wwwwwwwwwww";
        private String sss = "wwwwwwwwwww";
        protected String ssss = "wwwwwwwwwww";
    }
}