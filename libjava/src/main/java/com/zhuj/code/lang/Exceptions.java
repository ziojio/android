package com.zhuj.code.lang;

public class Exceptions {
    private Exceptions() {
    }

    public static void illegalArgument(String message) {
        throw new IllegalArgumentException(message);
    }

    public static void illegalState(String message) {
        throw new IllegalStateException(message);
    }

}
