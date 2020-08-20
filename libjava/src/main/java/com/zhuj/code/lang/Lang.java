package com.zhuj.code.lang;

import com.google.common.base.Functions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Lang {
    private Lang() {

    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else {
            return obj instanceof Map && ((Map<?, ?>) obj).isEmpty();
        }
    }

    public static <T> boolean isEmptyArray(T[] arr) {
        return null == arr || arr.length == 0;
    }

    @SafeVarargs
    public static <T> List<T> asList(T... e) {
        return new ArrayList<>(Arrays.asList(e));
    }

    public static int sumInt(int[] ints) {
        int num = 0;
        for (int i : ints) {
            num += i;
        }
        return num;
    }
}
