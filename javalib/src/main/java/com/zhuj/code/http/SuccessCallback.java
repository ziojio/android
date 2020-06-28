package com.zhuj.code.http;

public abstract class SuccessCallback implements com.zhuj.jbim.http.HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
    }

}
