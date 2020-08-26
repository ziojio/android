package com.zhuj.android.android;

import com.zhuj.android.util.Exceptions;
import com.zhuj.code.lang.StringUtils;

public class ObjectUtils {
    private ObjectUtils() {
    }

    public static void requireNotBlank(String str, String msg) {
        if (StringUtils.isNotBlank(str)) {
            Exceptions.throwIllegalArgument(msg);
        }
    }
}
