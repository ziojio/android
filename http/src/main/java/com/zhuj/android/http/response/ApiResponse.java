package com.zhuj.android.http.response;

public class ApiResponse<T> {

    //    @SerializedName(value = "ret", alternate = {"code", "retCode"})
    int ret;
    String msg;
    T data;

    public ApiResponse(int ret, String msg, T data) {
        this.ret = ret;
        this.msg = msg;
        this.data = data;
    }
}