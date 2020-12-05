package com.zhuj.android.util.util;



import android.net.Uri;

import com.zhuj.code.model.ListMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UriUtils {
    private final Map<String, String> map = new HashMap<>();

    private UriUtils() {
    }

    /**
     * 不能包含相同查询参数的key值
     *
     * @param uri
     */
    public static Map<String, String> fromUriQueryParameter(Uri uri) {
        if (uri == null || uri.getQuery() == null) return null;
        Map<String, String> map = new LinkedHashMap<>();
        for (String s : uri.getQueryParameterNames()) {
            String value = uri.getQueryParameter(s);
            map.put(s, value);
        }
        return map;
    }

    public static ListMap<String, String> fromUriQueryParameters(Uri uri) {
        if (uri == null || uri.getQuery() == null) return null;
        ListMap<String, String> map = new ListMap<>(new LinkedHashMap<>());
        for (String s : uri.getQueryParameterNames()) {
            map.add(s, uri.getQueryParameters(s));
        }
        return map;
    }
}
