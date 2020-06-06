package com.zhuj.android.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class Toasts {

    public static void show(Activity activity, String msg) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
    }

    public static void showLong(Activity activity, String msg) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("ShowToast")
    public static void show(Context context, String msg) {
        runOnMain(Toast.makeText(context, msg, Toast.LENGTH_SHORT));
    }

    @SuppressLint("ShowToast")
    public static void showLong(Context context, String msg) {
        runOnMain(Toast.makeText(context, msg, Toast.LENGTH_LONG));
    }

    private static void runOnMain(Toast toast) {
        if ("main".equals(Thread.currentThread().getName())) {
            toast.show();
        } else {
            new Handler(Looper.getMainLooper()).post(toast::show);
        }
    }

}