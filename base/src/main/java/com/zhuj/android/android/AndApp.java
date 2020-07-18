package com.zhuj.android.android;

import android.annotation.SuppressLint;
import android.content.Context;

public class AndApp {
    private AndApp() {
    }

    @SuppressLint("StaticFieldLeak")
    private static Context appContext;

    public static void initialize(Context appContext) {
        AndApp.appContext = appContext;
    }

    public static Context getContext() {
        return appContext;
    }
}
