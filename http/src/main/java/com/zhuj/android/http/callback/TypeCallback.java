package com.zhuj.android.http.callback;



import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class TypeCallback<T> extends AbstractCallback<T> {

    private Type type;

    public TypeCallback(Type type) {
        this.type = type;
    }

    public TypeCallback(Type type, boolean mainThreadCallback) {
        super(mainThreadCallback);
        this.type = type;
    }

    @Override
    T parseResponse(Call call, String body) {
        return JSONObject.parseObject(body, type);
    }
}
