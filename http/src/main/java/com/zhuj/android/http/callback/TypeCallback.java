package com.zhuj.android.http.callback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zhuj.code.util.GsonUtils;

import java.lang.reflect.Type;

import okhttp3.Call;

public abstract class TypeCallback<T> extends AbstractCallback {

    private Type type;

    public TypeCallback() {
        this.type = new TypeReference<T>() {
        }.getType();
    }

    public TypeCallback(boolean mainThreadCallback) {
        super(mainThreadCallback);
        this.type = new TypeReference<T>() {
        }.getType();
    }

    T parseResponse(Call call, String body) {

        return GsonUtils.fromJson(body, type);
    }
}
