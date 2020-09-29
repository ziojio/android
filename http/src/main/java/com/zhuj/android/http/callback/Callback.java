package com.zhuj.android.http.callback;


import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public abstract class Callback<T> implements okhttp3.Callback {

    @Override
    public void onResponse(@NonNull Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            ApiResult<T> result;
            try {
                String bodyStr = Objects.requireNonNull(response.body()).string();
                result = new Gson().fromJson(bodyStr, new ApiResultTypeImpl(getClass()));
            } catch (Exception e) { // 捕获 type get error
                onFailure(call, new IOException("parser json error: " + e.getMessage()));
                return;
            }
            if (result == null) {
                onFailure(call, new IOException("result is NULL"));
            } else if (!result.isSuccess()) {
                onFailure(call, new IOException("retCode error, ret=" + result.getCode() + ", msg=" + result.getMsg()));
            } else if (result.getData() == null) {
                onFailure(call, new IOException("data is NULL"));
            } else {
                try {
                    onSuccess(result.getData()); // 捕获执行出错的异常
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            onFailure(call, new IOException("response code=" + response.code() + ", msg=" + response.message()));
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        // Logger.e("Call: " + call.request().url() + ", msg: " + e.getMessage());
        onFailure(e);
    }

    public abstract void onSuccess(@NonNull T t);

    public abstract void onFailure(Exception e);

}