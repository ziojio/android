package com.zhuj.android.http.request.body;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 * Created by Zhenjie Yan on 2018/2/11.
 */
public class JsonBody extends StringBody {
    
    public JsonBody(String body) {
        this(body, StandardCharsets.UTF_8);
    }

    public JsonBody(String body, Charset charset) {
        super(body, charset, VALUE_APPLICATION_JSON);
    }
}