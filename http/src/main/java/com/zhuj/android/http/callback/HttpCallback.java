package com.zhuj.android.http.callback;


import com.zhuj.android.http.ErrorMessage;

public interface HttpCallback<T> {

    void onFailure(ErrorMessage e);

    void onSuccess(T body);

}
