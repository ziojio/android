package com.zhuj.code.util;


public class ObjectUtils {
    private ObjectUtils() {
    }

    public static String getName(final Object obj) {
        return obj == null ? "NULL" : obj.getClass().getName();
    }
}