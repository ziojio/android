package com.zhuj.android.http;


public class HttpException {
    public int code;
    public String msg;
    public Throwable error;

    public HttpException(int code, String msg, Throwable e) {
        this.code = code;
        this.msg = msg;
        this.error = e;
    }

    public HttpException(int errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }

    public HttpException(int errorCode, Throwable cause) {
        this.code = errorCode;
        this.error = cause;
    }

    @Override
    public String toString() {
        return "HttpException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}
