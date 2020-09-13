package com.zhuj.code.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtils {

    private JSONUtils() {
    }

    public static String toPrettyString(Object o) {
        return JSON.toJSONString(o, SerializerFeature.PrettyFormat);
    }

}
