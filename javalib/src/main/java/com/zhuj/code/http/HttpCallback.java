package com.zhuj.jbim.http;


import com.zhuj.code.http.ErrorMessage;

public interface HttpCallback<T> {

    void onFailure(ErrorMessage e);

    void onSuccess(T body);

}
