package com.zhuj.code.http;

public interface ProgressListener<T> {
    int onProgress(int progress);

    void onSuccess(T t);

    void onFailure(ErrorMessage e);
}
