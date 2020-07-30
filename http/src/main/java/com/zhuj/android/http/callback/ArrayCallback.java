package com.zhuj.android.http.callback;


import com.google.gson.reflect.TypeToken;

import java.util.List;

public abstract class ArrayCallback<T> extends TypeCallback<List<Class<T>>> {

    /**
     * @param mainThreadCallback true表示切到主线程再回调子类方法，
     */
    public ArrayCallback(Class<T> clazz, boolean mainThreadCallback) {
        super(new TypeToken<List<T>>() {}.getType(), mainThreadCallback);
    }
}
