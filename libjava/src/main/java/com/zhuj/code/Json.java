package com.zhuj.code;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.zhuj.code.util.TypeToken;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public abstract class Json<JsonObject, JsonArray> {
    protected static Type list;
    protected static boolean isDebug = true;

    protected static void showException(Exception e) {
        if(!isDebug) return;
        e.printStackTrace();
        // StackTraceElement[] trace = e.getStackTrace();
        // for (StackTraceElement traceElement : trace)
        //     logger.info("\tat " + traceElement);
    }

    public abstract String toJson(Object object);

    public abstract <T> T parseJson(String json, Class<T> classOfT);

    public abstract <T> T parseJson(String json, Type typeOfT);

    public abstract JsonObject parseJsonObject(String json);

    public abstract JsonArray parseJsonArray(String json);

    public abstract <T> List<T> parseList(String json);

    public abstract <T> Map<String, T> parseMap(String json);
}
