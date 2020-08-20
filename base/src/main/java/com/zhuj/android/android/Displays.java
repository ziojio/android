package com.zhuj.android.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

public class Displays {
    private Displays() {
    }
 
    public static DisplayMetrics getDisplayMetrics(Context context, boolean isReal) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (isReal) {
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        } else {
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics;
    }
 
    public static void logDisplayMetrics(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context, false);
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
        Log.d("Displays", "widthPixels=" + widthPixels + ", heightPixels=" + heightPixels +
                        ", densityDpi=" + densityDpi + ", density=" + density + ", scaledDensity=" + scaledDensity);
    }

    public static DisplayMetrics getDisplayMetrics(Context context, boolean isReal) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert windowManager != null;
        if (isReal) {
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        } else {
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    public static float getScaledDensity(Context context) {
        return getDisplayMetrics(context).scaledDensity;
    }

    public static int pxToDp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getDisplayMetrics(context));
    }

    public static int pxToSp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getDisplayMetrics(context));
    }

    public static int dpToPx(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getDisplayMetrics(context));
    }

    public static int spToPx(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getDisplayMetrics(context));
    }

}
