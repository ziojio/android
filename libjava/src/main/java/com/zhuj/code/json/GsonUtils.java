package com.zhuj.code.json;

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
import com.zhuj.code.lang.TypeUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    private static final Gson gsonPretty = new GsonBuilder()
            .setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    private static final Gson gsonExpose = new GsonBuilder().disableHtmlEscaping().serializeNulls()
            .excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    private GsonUtils() {
    }
    public static Gson getGson() {
        return gson;
    }

    public static Gson getGsonExpose() {
        return gsonExpose;
    }

    public static Gson getGsonPretty() {
        return gsonPretty;
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
    public static <T> T fromJson(JsonElement json, Type type) {
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
    public static <T> List<T> getList(JsonElement element, String name, Class<T> tClass) {
        if (element == null || !element.isJsonObject()) {
            return null;
        }
        try {
            return getGson().fromJson(element.getAsJsonObject().get(name),
                    TypeUtils.newParameterizedType(List.class, tClass));
        } catch (Exception e) {
            return null;
        }
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
        try {
            gson.fromJson(json, new TypeToken<T>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(JsonElement object, String name) {
        try {
            return object.getAsJsonObject().get(name).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInt(JsonElement object, String name, int defValue) {
        try {
            return object.getAsJsonObject().get(name).getAsInt();
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long getLong(JsonElement object, String name, long defValue) {
        try {
            return object.getAsJsonObject().get(name).getAsLong();
        } catch (Exception e) {
            return defValue;
        }
    }

    public <T> Map<String, T> parseMap(String json) {
        return null;
    }
}
