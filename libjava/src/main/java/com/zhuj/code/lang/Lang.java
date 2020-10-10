package com.zhuj.code.lang;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lang {
    private Lang() {
    }

    @SafeVarargs
    public static <T> List<T> asList(T... e) {
        return new ArrayList<>(Arrays.asList(e));
    }


}
