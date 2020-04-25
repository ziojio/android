package com.jbzh.android.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
    private DisplayUtil() { }

    private static float density;
    private static float scaledDensity;
    private static int widthPixels;
    private static int heightPixels;

    private static boolean isInit = false;

    private static void confirmInit() {
        if (!isInit) {
            throw new IllegalStateException("ViewUtil还未初始化");
        }
    }
    public static void init(Context context) {
        if (isInit) {
            return;
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        density = displayMetrics.density;
        scaledDensity = displayMetrics.scaledDensity;
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
        isInit = true;
    }

    public static float getDensity() {
        confirmInit();
        return density;
    }

    public static float getScaledDensity() {
        confirmInit();
        return scaledDensity;
    }

    public static int getScreenWidthPx() {
        confirmInit();
        return widthPixels;
    }

    public static int getScreenHeightPx() {
        confirmInit();
        return heightPixels;
    }

    /* 单位转换 */
    public static int dpToPx(float dpValue) {
        confirmInit();
        return (int) (dpValue * getDensity() + 0.5f);
    }

    public static int pxToDp(float pxValue) {
        confirmInit();
        return (int) (pxValue / getDensity() + 0.5F);
    }

    public static int pxToSp(float pxValue) {
        confirmInit();
        return (int) (pxValue / getScaledDensity() + 0.5f);
    }

    public static int spToPx(float spValue) {
        confirmInit();
        return (int) (spValue * getScaledDensity() + 0.5f);
    }
}
