package com.zhuj.android.logger;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static ILogger sILogger = new LogcatLogger();

    private static String sTag = DEFAULT_LOG_TAG;
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
    public static void setTag(String tag) {
        sTag = tag;
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

    public void json(String json) {
        String msg = "json is NULL";
        if (json != null) {
            try {
                msg = "\n" + new JSONObject(json).toString(4);
            } catch (JSONException e) {
                msg = "json parse ERROR msg=" + e.getMessage();
            }
        }
        if (enableLog(Log.INFO)) {
            sILogger.log(Log.INFO, sTag, msg, null);
        }
    }

    public void json(String tag, String json) {
        String msg = "json is NULL";
        if (json != null) {
            try {
                msg = "\n" + new JSONObject(json).toString(4);
            } catch (JSONException e) {
                msg = "json parse ERROR msg=" + e.getMessage();
            }
        }
        if (enableLog(Log.INFO)) {
            sILogger.log(Log.INFO, tag, msg, null);
        }
    }

    /**
     * 打印任何（所有）信息
     *
     * @param msg
     */
    public static void v(String msg) {
        if (enableLog(Log.VERBOSE)) {
            sILogger.log(Log.VERBOSE, sTag, msg, null);
        }
    }

    /**
     * 打印任何（所有）信息
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (enableLog(Log.VERBOSE)) {
            sILogger.log(Log.VERBOSE, tag, msg, null);
        }
    }

    /**
     * 打印调试信息
     *
     * @param msg
     */
    public static void d(String msg) {
        if (enableLog(Log.DEBUG)) {
            sILogger.log(Log.DEBUG, sTag, msg, null);
        }
    }

    /**
     * 打印调试信息
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (enableLog(Log.DEBUG)) {
            sILogger.log(Log.DEBUG, tag, msg, null);
        }
    }

    /**
     * 打印调试信息
     *
     * @param tag
     * @param format
     * @param args
     */
    public static void d(String tag, String format, Object... args) {
        if (enableLog(Log.DEBUG)) {
            sILogger.log(Log.DEBUG, tag, String.format(format, args), null);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param msg
     */
    public static void i(String msg) {
        if (enableLog(Log.INFO)) {
            sILogger.log(Log.INFO, sTag, msg, null);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (enableLog(Log.INFO)) {
            sILogger.log(Log.INFO, tag, msg, null);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param msg
     */
    public static void w(String msg) {
        if (enableLog(Log.WARN)) {
            sILogger.log(Log.WARN, sTag, msg, null);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (enableLog(Log.WARN)) {
            sILogger.log(Log.WARN, tag, msg, null);
        }
    }

    /**
     * 打印出错信息
     *
     * @param msg
     */
    public static void e(String msg) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, sTag, msg, null);
        }
    }

    /**
     * 打印出错信息
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, tag, msg, null);
        }
    }

    /**
     * 打印出错信息
     *
     * @param tag
     * @param format
     */
    public static void e(String tag, String format, Object... args) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, tag, String.format(format, args), null);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void e(String tag, String msg, Throwable t) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, tag, msg, t);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param t
     */
    public static void error(Throwable t) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, sTag, null, t);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param t
     */
    public static void error(String tag, Throwable t) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, tag, null, t);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param msg
     * @param t
     */
    public static void error(Throwable t, String msg) {
        if (enableLog(Log.ERROR)) {
            sILogger.log(Log.ERROR, sTag, msg, t);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param msg
     */
    public static void wtf(String msg) {
        if (enableLog(Log.ASSERT)) {
            sILogger.log(Log.ASSERT, sTag, msg, null);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param tag
     * @param msg
     */
    public static void wtf(String tag, String msg) {
        if (enableLog(Log.ASSERT)) {
            sILogger.log(Log.ASSERT, tag, msg, null);
        }
    }

    /**
     * 能否打印
     *
     * @param logPriority
     */
    private static boolean enableLog(int logPriority) {
        if (sILogger == null) {
            Log.w(sTag, "Logger.ILogger is NULL");
            return false;
        } else if (!sIsDebug) {
            Log.w(sTag, "Logger.isDebug is FALSE");
            return false;
        }
        return logPriority >= sLogPriority;
    }
}
