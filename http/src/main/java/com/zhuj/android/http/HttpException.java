package com.zhuj.android.http;


public class HttpException {
    public int code;
    public String msg;
    public Throwable e;

    public HttpException(int code, String msg, Throwable e) {
        this.code = code;
        this.msg = msg;
        this.e = e;
    }

    public HttpException(int errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }

    public HttpException(Throwable cause) {
        e = cause;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorCode=" + code +
                ", msg='" + msg + '\'' +
                ", e=" + e +
                '}';
    }
}
