package com.zhuj.code.lang;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lang {
    private Lang() {
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
