package com.zhuj.android.android;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * 添加一些方便的log方法
 */
public class Logs {
    private Logs() {
    }

    public static boolean isShowFullClassMethod = false;
    public static boolean isShowFileAndLineNumber = true;
    /**
     * 当前的栈偏移，打印的栈跟踪 指定栈 - 1
     */
    public static int currentStackOffset = 5;

    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;

    public static StackTraceElement getCurrentStackTraceElement() {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        // for (StackTraceElement element : stackTraceElements) {
        //     Log.d("logs", "----------------------------------------------------------");
        //     Log.d("logs", element.toString());
        //     Log.d("logs", String.format("className=%s, methodName=%s", element.getClassName(), element.getMethodName()));
        //     Log.d("logs", String.format("fileName=%s, lineNumber=%s", element.getFileName(), element.getLineNumber()));
        //     Log.d("logs", "----------------------------------------------------------");
        // }
        return stackTraceElements[currentStackOffset];
    }

    public static String getMethodString() {
        StackTraceElement element = getCurrentStackTraceElement();
        String msgm;
        if (isShowFullClassMethod) {
            msgm = element.getClassName() + "." + element.getMethodName();
        } else {
            msgm = element.getMethodName();
        }
        if (isShowFileAndLineNumber) {
            msgm = msgm + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
        }
        return msgm;
    }

    private static void log(int priority, Object tag, String msg, Object... objects) {
        String msgf;
        if (objects.length == 0) {
            msgf = msg;
        } else {
            msgf = String.format(msg, objects);
        }
        String tagf;
        if (tag instanceof String) {
            tagf = (String) tag;
        } else {
            tagf = tag.getClass().getSimpleName();
        }
        log(priority, tagf, msgf);
    }

    private static void log(int priority, String tag, String msg) {
        msg = getMethodString() + ": " + msg;
        switch (priority) {
            case VERBOSE:
                Log.v(tag, msg);
                break;
            case DEBUG:
                Log.d(tag, msg);
                break;
            case INFO:
                Log.i(tag, msg);
                break;
            case WARN:
                Log.w(tag, msg);
                break;
            case ERROR:
                Log.e(tag, msg);
                break;
            case ASSERT:
                Log.wtf(tag, msg);
                break;
        }
    }

    public static void json(Object tag, String json) {
        String msg = "json is NULL";
        if (json != null) {
            try {
                msg = new JSONObject(json).toString(4);
            } catch (JSONException e) {
                msg = "json parse ERROR msg=" + e.getMessage();
            }
        }
        log(DEBUG, tag, msg);
    }

    public static void array(Object tag, Object[] objects) {
        String msg = Arrays.toString(objects);
        log(DEBUG, tag, msg);
    }

    public static void d(Object tag, String msg, Object... objects) {
        log(DEBUG, tag, msg, objects);
    }

    public static void i(Object tag, String msg, Object... objects) {
        log(INFO, tag, msg, objects);
    }

    public static void w(Object tag, String msg, Object... objects) {
        log(WARN, tag, msg, objects);
    }

    public static void e(Object tag, String msg, Object... objects) {
        log(ERROR, tag, msg, objects);
    }

}