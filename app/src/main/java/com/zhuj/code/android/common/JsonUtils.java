package com.zhuj.code.android.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public final class JsonUtils {

    private JsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    public static Gson getGson() {
        return gson;
    }

    public static void setGson(Gson mGson) {
        gson = mGson;
    }

    //  反序列化一个 json 为一个给定的 Class<T>.class
    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  @param typeOfT 泛型类
    //  Array         的泛型Type  Class[].class -> int[].class / Integer[].class
    //  ArrayList<T>  的泛型Type  new TypeToken< ArrayList<Founder> >(){}.getType();
    //        使用一个 Class 包含 泛型List<T> 可以直接传递 Class<T>.class
    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return gson.fromJson(json, typeOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject toJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * @param json
     * @return org.json 包下的 Class
     */
    public static JSONObject toJSONObject(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
