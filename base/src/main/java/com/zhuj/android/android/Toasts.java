package com.zhuj.android.android;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

public class Toasts {
    private Toasts() {
    }

    public static void show(Context context, String msg) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            Androids.runOnUiThread(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
        }
    }

    public static void showLong(Context context, String msg) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        } else {
            Androids.runOnUiThread(() -> Toast.makeText(context, msg, Toast.LENGTH_LONG).show());
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