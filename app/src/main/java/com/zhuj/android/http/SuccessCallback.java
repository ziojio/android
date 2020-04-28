package com.zhuj.android.http;

import android.util.Log;

public abstract class SuccessCallback implements com.jbzh.jbim.http.HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
        Log.d("ApiResponse", e.toString());
    }

}
