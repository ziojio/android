package com.zhuj.code;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.zhuj.code.lang.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AppMain {
    public static Logger logger = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {

        String s = "";


    }

    static class Mt {
        String defaultStr = "default field";
        private String privateStr = "private field";
        protected String protectedStr = "protected field";
        public String publicStr = "public field";
    }
}