package com.zhuj.android.android;

import com.zhuj.android.util.Exceptions;
import com.zhuj.code.util.Strings;

public class ObjectUtils {
    private ObjectUtils() {
    }

    public static void requireNotBlank(String str, String msg) {
        if (Strings.isNotBlank(str)) {
            Exceptions.throwIllegalArgument(msg);
        }
    }
}
