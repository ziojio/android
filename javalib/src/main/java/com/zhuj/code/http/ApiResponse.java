package com.zhuj.code.http;

/**
 * 接口返回结果
 * 与接口返回的格式对应，即有：ret/data/msg
 */
public class ApiResponse {
    private int ret;
    private String data;
    private String msg;

    /**
     * @param ret  response code
     * @param data json content
     * @param msg  ...
     */
    public ApiResponse(int ret, String data, String msg) {
        this.ret = ret;
        this.data = data;
        this.msg = msg;
    }

    public ApiResponse(int ret, String data) {
        this(ret, data, "");
    }

    public ApiResponse(int ret) {
        this(ret, "", "");
    }

    public int getRet() {
        return this.ret;
    }

    public String getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "ApiResponse {" +
                "ret=" + ret +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }


}