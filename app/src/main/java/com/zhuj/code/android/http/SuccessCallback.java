package com.zhuj.code.android.http;

import android.util.Log;

import com.zhuj.code.android.http.ApiResponse;
import com.zhuj.code.android.http.ErrorMessage;

public abstract class SuccessCallback implements com.jbzh.jbim.http.HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
        Log.d("ApiResponse", e.toString());
    }

}
