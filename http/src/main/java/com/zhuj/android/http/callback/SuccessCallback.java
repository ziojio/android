package com.zhuj.android.http.callback;

import com.zhuj.android.http.data.ApiResponse;
import com.zhuj.android.http.ErrorMessage;

public abstract class SuccessCallback implements HttpCallback<ApiResponse> {
    @Override
    public void onFailure(ErrorMessage e) {
    }

}
