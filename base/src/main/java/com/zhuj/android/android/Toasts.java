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
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(String msg) {
        show(Androids.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    public static void showLong(String msg) {
        show(Androids.getContext(), msg, Toast.LENGTH_LONG);
    }

    public static void show(Context context, String msg, int duration) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Toast.makeText(context, msg, duration).show();
        } else {
            Androids.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, duration).show();
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