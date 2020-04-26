package com.jbzh.jbim.http;


import com.zhuj.code.android.http.ErrorMessage;

public interface HttpCallback<T> {

    void onFailure(ErrorMessage e);

    void onSuccess(T body);

}
