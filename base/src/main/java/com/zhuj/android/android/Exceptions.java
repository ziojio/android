package com.zhuj.android.android;

public class Exceptions {
    public static void illegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    public static void illegalState(String msg, Object... params) {
        throw new IllegalStateException(String.format(msg, params));
    }


}
