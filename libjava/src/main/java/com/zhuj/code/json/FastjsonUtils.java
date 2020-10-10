package com.zhuj.code.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastjsonUtils {

    private FastjsonUtils() {
    }

    public static String toPrettyString(Object o) {
        return JSON.toJSONString(o, SerializerFeature.PrettyFormat);
    }

}
