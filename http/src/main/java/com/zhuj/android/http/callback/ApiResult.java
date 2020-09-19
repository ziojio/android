package com.zhuj.android.http.callback;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {
    public final static String CODE = "code";
    public final static String MSG = "msg";
    public final static String DATA = "data";

    @SerializedName(value = CODE, alternate = {"ret", "Code"})
    private int Code;
    @SerializedName(value = MSG, alternate = {"message", "Msg"})
    private String Msg;
    @SerializedName(value = DATA, alternate = {"Data"})
    private T Data;

    public int getCode() {
        return Code;
    }

    public ApiResult<T> setCode(int code) {
        Code = code;
        return this;
    }

    public String getMsg() {
        return Msg;
    }

    public ApiResult<T> setMsg(String msg) {
        Msg = msg;
        return this;
    }

    public ApiResult<T> setData(T data) {
        Data = data;
        return this;
    }

    /**
     * 获取请求响应的数据，自定义api的时候需要重写【很关键】
     */
    public T getData() {
        return Data;
    }

    /**
     * 是否请求成功,自定义api的时候需要重写【很关键】
     */
    public boolean isSuccess() {
        return getCode() == 200;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "Code=" + Code +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
