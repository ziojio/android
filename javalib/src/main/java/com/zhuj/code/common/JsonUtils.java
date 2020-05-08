package com.zhuj.code.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


import java.lang.reflect.Type;

public final class JsonUtils {

    private JsonUtils() {
    }

    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    public static Gson getGson() {
        if (gson == null) {
            synchronized (JsonUtils.class) {
                if (gson == null) {
                    gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
                }
            }
        }
        return gson;
    }

    public static void setGson(Gson mGson) {
        gson = mGson;
    }


    //  @param typeOfT 泛型类
    //  Array         的泛型Type  Class[].class -> int[].class / Integer[].class
    //  ArrayList<T>  的泛型Type  new TypeToken< ArrayList<Founder> >(){}.getType();
    //  使用一个 Class 包含 泛型List<T> 可以直接传递 Class<T>.class
    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return gson.fromJson(json, typeOfT);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject toJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

}
