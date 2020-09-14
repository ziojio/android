package com.zhuj.code;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhuj.code.util.GsonUtils;
import com.zhuj.code.util.JacksonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public abstract class Callback<T> {
    public static Logger logger = LoggerFactory.getLogger(Callback.class);

    public void onResponse(String response) {
        T result;
        logger.info(getClass().getGenericSuperclass().getTypeName());
        ParameterizedType parameterizedType;
        try {
            parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            logger.info(Arrays.toString(parameterizedType.getActualTypeArguments()));
        } catch (Exception ignored) {
            return;
        }
          result = JSONObject.parseObject(response, parameterizedType.getActualTypeArguments()[0]);
        // result = JacksonUtils.fromJson(response, parameterizedType.getActualTypeArguments()[0]);
        // result = GsonUtils.fromJson(response, parameterizedType.getActualTypeArguments()[0]);
        logger.info("result: " + result);

    }
}