package com.zhuj.code;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class JsonUtils {
    protected static Type list;
    protected static boolean isDebug = true;

    public static void showException(Exception e) {
        if (!isDebug) return;
        e.printStackTrace();
        // StackTraceElement[] trace = e.getStackTrace();
        // for (StackTraceElement traceElement : trace)
        //     logger.info("\tat " + traceElement);
    }

    public String toJson(Object object) {
        return null;
    }

    public <T> T parseJson(String json, Class<T> classOfT) {
        return null;
    }

    public <T> T parseJson(String json, Type typeOfT) {
        return null;
    }

    public JsonObject parseJsonObject(String json) {
        return null;
    }

    public JsonArray parseJsonArray(String json) {
        return null;
    }

    public <T> List<T> parseList(String json) {
        return null;
    }

    public <T> Map<String, T> parseMap(String json) {
        return null;
    }
}
