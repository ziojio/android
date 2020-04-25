package com.jbzh.jbim.http;


import com.jbzh.jbim.ErrorMessage;

public interface HttpCallback<T> {

    void onFailure(ErrorMessage e);

    void onSuccess(T body);

}
