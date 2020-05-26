package com.zhuj.android.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class Androids {
    private Resources resources;

    private Androids(Application app) {
        resources = app.getResources();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSystemService(Context context, String serviceName) {
        return (T) context.getSystemService(serviceName);
    }

}