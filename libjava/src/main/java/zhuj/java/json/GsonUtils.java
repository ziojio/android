package zhuj.java.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zhuj.java.lang.TypeUtils;


public class GsonUtils {
    private static final Gson gsonPretty = new GsonBuilder()
            .setPrettyPrinting().serializeNulls().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static final Gson gson = new GsonBuilder()
            .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static final Gson gsonLenient = new GsonBuilder()
            .serializeNulls().setLenient().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private static final Gson gsonExpose = new GsonBuilder()
            .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

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

    public static Gson getGsonLenient() {
        return gsonLenient;
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toPrettyJson(Object obj) {
        return decodeUnicode(gsonPretty.toJson(obj));
    }

    public static String decodeUnicode(String str) {
        if (str == null) return null;
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = str.length();
        for (int i = 0; i < maxLoop; i++) {
            if (str.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((str.charAt(i + 1) == 'u') || (str.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(str.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(str.charAt(i));
                    }
                else
                    retBuf.append(str.charAt(i));
            } else {
                retBuf.append(str.charAt(i));
            }
        }
        return retBuf.toString();
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
    }

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

    public static JsonObject parseJsonObject(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonArray parseJsonArray(String json) {
        try {
            JsonElement element = JsonParser.parseString(json);
            return element.getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(JsonElement object, String name) {
        if (object == null) return null;
        try {
            return object.getAsJsonObject().get(name).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean getIsNull(JsonElement object, String name) {
        if (object == null) return true;
        try {
            return object.getAsJsonObject().get(name) == null;
        } catch (Exception e) {
            return true;
        }
    }

    public static int getInt(JsonElement object, String name, int defValue) {
        if (object == null) return defValue;
        try {
            return object.getAsJsonObject().get(name).getAsInt();
        } catch (Exception e) {
            return defValue;
        }
    }

    public static long getLong(JsonElement object, String name, long defValue) {
        if (object == null) return defValue;
        try {
            return object.getAsJsonObject().get(name).getAsLong();
        } catch (Exception e) {
            return defValue;
        }
    }

    public static List<JsonObject> toListJsonObject(JsonArray jsonArray) {
        if (jsonArray == null || jsonArray.size() == 0) {
            return Collections.emptyList();
        }
        List<JsonObject> list = new ArrayList<>();
        for (JsonElement e : jsonArray) {
            list.add(e.getAsJsonObject());
        }
        return list;
    }
}
