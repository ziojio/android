package com.zhuj.android;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.zhuj.android.android.Androids;
import com.zhuj.android.android.Displays;
import com.zhuj.android.android.Logs;

public class AppRes {

    public AppRes(Context context) {
//        WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        WindowManager wm = Androids.getSystemService(context,Context.WINDOW_SERVICE);
        DisplayMetrics metrics;
        if (wm == null) {
            metrics = context.getResources().getDisplayMetrics();
        } else {
            metrics = Displays.getDisplayMetrics(context, true);
            if (metrics == null) {
                metrics = context.getResources().getDisplayMetrics();
            }
        }
        Logs.logDisplayMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        screenDensity = metrics.density;
    }


    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;


}
