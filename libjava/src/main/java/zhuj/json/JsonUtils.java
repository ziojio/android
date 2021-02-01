package zhuj.json;

import java.lang.reflect.Type;

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

    public <T> T fromJson(String json, Type type) {

        return null;
    }


}
