package com.zhuj.code.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    private static Gson gson = new GsonBuilder().setPrettyPrinting()
            .serializeNulls().disableHtmlEscaping().create();

    private GsonUtils() {
    }

    public static void setGson(Gson gson) {
        GsonUtils.gson = gson;
    }

    public static String toJson(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Array         的泛型Type  Class[].class -> int[].class / Integer[].class
     * ArrayList<T>  的泛型Type  new TypeToken<ArrayList<Object> >(){}.getType();
     * 使用一个 Class 包含泛型List<T> 可以直接传递 Class<T>.class
     *
     * @param type 泛型类
     */
    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject parseJsonObject(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonObject();
        } catch (JsonSyntaxException | IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonArray parseJsonArray(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonArray();
        } catch (JsonSyntaxException | IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> parseList(String json) {
        new Gson().fromJson(json, new TypeToken<T>(){}.getType());
        return null;
    }


    public <T> Map<String, T> parseMap(String json) {
        return null;
    }
}
