package com.zhuj.android.http.callback;


import com.zhuj.android.http.HttpException;

public interface HttpCallback<T> {

    void onFailure(HttpException e);

    void onSuccess(T body);

}
