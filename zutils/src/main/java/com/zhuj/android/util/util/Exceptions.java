package com.zhuj.android.util.util;

import android.util.Log;

import java.util.Locale;

public class Exceptions {
    private static final String TAG = Exceptions.class.getSimpleName();

    private Exceptions(){
    }

    public static void throwIllegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    public static void throwIllegalState(String msg, Object... params) {
        throw new IllegalStateException(String.format(msg, params));
    }

    public static void handleException(Exception e, String format, Object... params) {
        if (params != null) {
            Log.e(TAG, String.format(Locale.CHINA, format, params));
        }
        if (format != null) {
            Log.e(TAG, format);
        }
        Log.e(TAG, e.getMessage() + "\n -------------------------------------------------------------------------------- ");
        e.printStackTrace();
    }

}
