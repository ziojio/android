package zhuj.http.callback;

import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {

    public final static String CODE = "code";
    public final static String MSG = "msg";
    public final static String DATA = "data";

    @SerializedName(value = CODE, alternate = {"ret"})
    private int code;
    @SerializedName(value = MSG, alternate = {"message"})
    private String msg;
    @SerializedName(value = DATA)
    private T data;

    public int getCode() {
        return code;
    }

    public ApiResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 获取请求响应的数据，自定义api的时候需要重写【很关键】
     */
    public T getData() {
        return data;
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
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
