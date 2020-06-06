package com.zhuj.android.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

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
