package com.zhuj.android.android;


import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

public class Times {
    private static final String TAG = "Times";

    private Times() {
    }

    public static void runMainDelayed(Runnable run, long ms) {
        new Handler(Looper.getMainLooper()).postDelayed(run, ms);
    }

    public static void runDelayed(Runnable run, long ms) {
        new CountDownTimer(ms, ms) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                new Thread(run).start();
            }
        }.start();
    }
}
