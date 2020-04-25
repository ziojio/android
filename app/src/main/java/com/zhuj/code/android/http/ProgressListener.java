package com.jbzh.jbim.http;


import com.jbzh.jbim.ErrorMessage;

public interface ProgressListener<T> {
    int onProgress(int progress);

    void onSuccess(T t);

    void onFailure(ErrorMessage e);
}
