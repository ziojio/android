package zhuj.utils.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;

public final class Logger {
    /**
     * Don't let anyone instantiate this class.
     */
    private Logger() {
        throw new UnsupportedOperationException("Do not need instantiate!");
    }

    //==============常量================//
    /**
     * 默认tag
     */
    public final static String DEFAULT_LOG_TAG = "[Logger]";
    /**
     * 最大日志优先级【日志优先级为最大等级，所有日志都不打印】
     */
    private final static int MAX_LOG_PRIORITY = 10;
    /**
     * 最小日志优先级【日志优先级为最小等级，所有日志都打印】
     */
    private final static int MIN_LOG_PRIORITY = 0;

    //==============属性================//
    /**
     * 默认的日志记录为Logcat
     */
    private static ILogger sILogger = new LogcatLogger() {
        @Override
        public boolean isLoggable(int priority, @Nullable String tag) {
            if (!sIsDebug) {
                Log.w(sTag, "Logger.isDebug is FALSE");
                return false;
            }
            return priority >= sLogPriority;
        }
    };

    /**
     * 全局标签，不指定TAG时使用
     * 设置 NOT NULL, 显示设置的值
     * 设置为 NULL, 显示所在的类名
     */
    private static String sTag = null;
    /**
     * 是否是调试模式
     */
    private static boolean sIsDebug = false;
    /**
     * 日志打印优先级
     */
    private static int sLogPriority = MAX_LOG_PRIORITY;

    //==============属性设置================//

    /**
     * 设置日志记录者的接口
     *
     * @param logger
     */
    public static void setLogger(@NonNull ILogger logger) {
        sILogger = logger;
    }

    /**
     * 设置日志的tag
     *
     * @param tag
     */
    public static void setGlobalTag(String tag) {
        sTag = tag;
    }

    /**
     * 设置当前线程日志的tag, 只使用一次， 前提是全局标签为 NULL
     *
     * @param tag
     */
    public static void setLocalTag(String tag) {
        sILogger.localTag.set(tag);
    }

    /**
     * 设置是否是调试模式
     *
     * @param isDebug
     */
    public static void setDebug(boolean isDebug) {
        sIsDebug = isDebug;
    }

    /**
     * 设置打印日志的等级（只打印改等级以上的日志）
     *
     * @param priority
     */
    public static void setPriority(int priority) {
        sLogPriority = priority;
    }

    //===================对外接口=======================//

    /**
     * 设置是否打开调试
     *
     * @param isDebug
     */
    public static void debug(boolean isDebug) {
        if (isDebug) {
            setDebug(true);
            setPriority(MIN_LOG_PRIORITY);
        } else {
            setDebug(false);
            setPriority(MAX_LOG_PRIORITY);
        }
    }

    //=============打印方法===============//

    public static void json(String json) {
        if (json == null || json.length() == 0) {
            e("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                i(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                i(message);
                return;
            }
            e("invalid json, must be start with { or [");
        } catch (JSONException e) {
            e("invalid json error: " + e.getMessage());
        }
    }

    public static void xml(String xml) {
        if (xml == null || xml.length() == 0) {
            e("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            i(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml error: " + e.getMessage());
        }
    }

    public static String logLevel(int priority) {
        switch (priority) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    public static void object(Object obj) {
        if (obj == null) {
            e("Object is NULL");
            return;
        }

        String msg = toString(obj);
        if (!msg.contains("Couldn't find")) {
            i(msg);
        } else {
            e(msg);
        }
    }

    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    /**
     * 打印任何（所有）信息
     *
     * @param msg
     */
    public static void v(String msg) {
        sILogger.prepareLog(Log.VERBOSE, sTag, null, msg);
    }

    public static void v(String tag, String msg) {
        sILogger.prepareLog(Log.VERBOSE, tag, null, msg);
    }

    /**
     * 打印调试信息
     *
     * @param msg
     */
    public static void d(String msg) {
        sILogger.prepareLog(Log.DEBUG, sTag, null, msg);
    }

    public static void d(String format, Object... args) {
        sILogger.prepareLog(Log.DEBUG, sTag, null, format, args);
    }

    public static void d(String tag, String msg) {
        sILogger.prepareLog(Log.DEBUG, tag, null, msg);
    }

    public static void d(String tag, String format, Object... args) {
        sILogger.prepareLog(Log.DEBUG, tag, null, format, args);
    }

    /**
     * 打印提示性的信息
     *
     * @param msg
     */
    public static void i(String msg) {
        sILogger.prepareLog(Log.INFO, sTag, null, msg);
    }

    public static void i(String tag, String msg) {
        sILogger.prepareLog(Log.INFO, tag, null, msg);
    }

    /**
     * 打印warning警告信息
     *
     * @param msg
     */
    public static void w(String msg) {
        sILogger.prepareLog(Log.WARN, sTag, null, msg);
    }

    public static void w(String tag, String msg) {
        sILogger.prepareLog(Log.WARN, tag, null, msg);
    }

    /**
     * 打印出错信息
     *
     * @param msg
     */
    public static void e(String msg) {
        sILogger.prepareLog(Log.ERROR, sTag, null, msg);
    }

    public static void e(String format, Object... args) {
        sILogger.prepareLog(Log.ERROR, sTag, null, format, args);
    }

    public static void e(String tag, String msg) {
        sILogger.prepareLog(Log.ERROR, tag, null, msg);
    }

    public static void e(String tag, String format, Object... args) {
        sILogger.prepareLog(Log.ERROR, tag, null, format, args);
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void e(String tag, String msg, Throwable t) {
        sILogger.prepareLog(Log.ERROR, tag, t, msg);
    }

    public static void e(Throwable t) {
        sILogger.prepareLog(Log.ERROR, sTag, t, null);
    }

    public static void e(String tag, Throwable t) {
        sILogger.prepareLog(Log.ERROR, tag, t, null);
    }

    public static void e(Throwable t, String msg) {
        sILogger.prepareLog(Log.ERROR, sTag, t, msg);
    }

    /**
     * 打印严重的错误信息
     *
     * @param msg
     */
    public static void wtf(String msg) {
        sILogger.prepareLog(Log.ASSERT, sTag, null, msg);
    }

    public static void wtf(String tag, String msg) {
        sILogger.prepareLog(Log.ASSERT, tag, null, msg);
    }

}
