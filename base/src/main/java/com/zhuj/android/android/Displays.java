package com.zhuj.android.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class Displays {
    private Displays() {
    }

    public static DisplayMetrics getDisplayMetrics(Context context, boolean isReal) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            if (isReal) {
                windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
            } else {
                windowManager.getDefaultDisplay().getMetrics(outMetrics);
            }
            return outMetrics;
        }
        return null;
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
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getScaledDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int getScreenWidthPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dpToPx(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static int pxToDp(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    public static int pxToSp(Context context, float pxValue) {
        return (int) (pxValue / getScaledDensity(context) + 0.5f);
    }

    public static int spToPx(Context context, float spValue) {
        return (int) (spValue * getScaledDensity(context) + 0.5f);
    }
}
