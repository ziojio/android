package com.zhuj.code;

import com.zhuj.code.util.Reflections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.out;

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