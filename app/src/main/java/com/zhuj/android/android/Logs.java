package com.zhuj.android.android;

import android.util.DisplayMetrics;
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

    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;

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
                msg = "json parse ERROR";
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


    public static void logDisplayMetrics(DisplayMetrics metrics) {
        // 可用显示大小的绝对宽度（以像素为单位）。
        int widthPixels = metrics.widthPixels;
        // 可用显示大小的绝对高度（以像素为单位）。
        int heightPixels = metrics.heightPixels;
        // 屏幕密度表示为每英寸点数。
        int densityDpi = metrics.densityDpi;
        // 显示器的逻辑密度。
        float density = metrics.density;
        // 显示屏上显示的字体缩放系数。
        float scaledDensity = metrics.scaledDensity;
        Log.d("Logs",
                "widthPixels=" + widthPixels +
                        ", heightPixels=" + heightPixels +
                        ", densityDpi=" + densityDpi +
                        ", density=" + density +
                        ", scaledDensity=" + scaledDensity);
    }
}
