package com.zhuj.code.android.http;

import com.zhuj.code.android.http.ErrorMessage;

public interface ProgressListener<T> {
    int onProgress(int progress);

    void onSuccess(T t);

    void onFailure(ErrorMessage e);
}
