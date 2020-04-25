package com.jbzh.jbim.http;

import android.util.Log;

import com.jbzh.jbim.ErrorMessage;

public abstract class SuccessCallback implements HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
        Log.d("ApiResponse", e.toString());
    }

}
