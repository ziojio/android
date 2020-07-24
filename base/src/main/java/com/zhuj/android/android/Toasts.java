package com.zhuj.android.android;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

public class Toasts {

    public static void show(Activity activity, String msg) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
    }

    public static void showLong(Activity activity, String msg) {
        activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
    }

    public static void show(Context context, String msg) {
        runOnMainThread(context, msg);
    }

    public static void showLong(Context context, String msg) {
        runOnMainThread(context, msg);
    }


    private static void runOnMainThread(final Context context, final String msg) {
        // Thread.currentThread() == Looper.getMainLooper().getThread()
        // "main".equals(Thread.currentThread().getName()
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static Toast makeText(Context context, @LayoutRes int layoutId, int duration) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        // TextView tv = view.findViewById(R.id.tv_info);
        // if (tv != null) {
        //     tv.setText(msg);
        //     if (tv.getBackground() != null) {
        //         tv.getBackground().setAlpha(100);
        //     }
        // }
        toast.setDuration(duration);
        return toast;
    }

}