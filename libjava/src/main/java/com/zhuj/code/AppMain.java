package com.zhuj.code;

import com.google.gson.JsonObject;
import com.zhuj.code.lang.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class AppMain {
    public static Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {

        // Reflections.logObjectField(new Mt(), "ss","sss", "ssss");
        String[] strings = new String[]{"adaf", "add"};
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "hello");
        jsonObject.addProperty("age", 18);
        jsonObject.addProperty("sex", true);
        String res = jsonObject.toString();
        new Callback<JsonObject>(){}.onResponse(res);

    }

    static class Mt {
        String ss = "wwwwwwwwwww";
        private String sss = "wwwwwwwwwww";
        protected String ssss = "wwwwwwwwwww";
    }
}