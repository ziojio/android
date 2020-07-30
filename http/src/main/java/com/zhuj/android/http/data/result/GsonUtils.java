package com.zhuj.android.http.data.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GsonUtils {
    private static Gson gson;

    private GsonUtils() {
    }

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
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
    public static String toJson(Object obj, Type type) {
        try {
            return gson.toJson(obj);
        } catch (JsonIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Array         的泛型Type  Class[].class -> int[].class / Integer[].class
     * ArrayList<T>  的泛型Type  new TypeToken< ArrayList<Founder> >(){}.getType();
     * 使用一个 Class 包含泛型List<T> 可以直接传递 Class<T>.class
     *
     * @param classOfT 泛型类
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }    /**
     * Array         的泛型Type  Class[].class -> int[].class / Integer[].class
     * ArrayList<T>  的泛型Type  new TypeToken< ArrayList<Founder> >(){}.getType();
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

    public static JsonObject toJsonObject(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonObject();
        } catch (JsonSyntaxException | IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonArray toJsonArray(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonArray();
        } catch (JsonSyntaxException | IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
