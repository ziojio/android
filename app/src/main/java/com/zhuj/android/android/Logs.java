package com.zhuj.android.android;

import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加一些方便的log方法
 */
public class Logs {
    private Logs() {
    }

    public static void d(Class<?> clazz, String msg, Object... objects) {
        String msgf = String.format(msg, objects);
        Log.d(clazz.getSimpleName(), msgf);
    }

    public static void d(Class<?> clazz, String json) {
        try {
            String str = "no json";
            if(json != null){
                 str = new JSONObject(json).toString(4);
            }
            Log.d(clazz.getSimpleName(), str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void w(Class<?> clazz, String msg, Object... objects) {
        String msgf = String.format(msg, objects);
        Log.w(clazz.getSimpleName(), msgf);
    }

    public static void e(Class<?> clazz, String msg, Object... objects) {
        String msgf = String.format(msg, objects);
        Log.e(clazz.getSimpleName(), msgf);
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
