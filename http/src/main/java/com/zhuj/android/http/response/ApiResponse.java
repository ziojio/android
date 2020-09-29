package com.zhuj.android.http.response;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {
 * "ret": 200, // 状态码
 * "data": {
 * // 业务数据，推荐统一返回对象结构，保持类型一致性，且便于扩展和升级
 * },
 * "msg": ""   // 错误提示信息
 * }
 */
@AllArgsConstructor
@Data
public class ApiResponse<T> {

    //    @SerializedName(value = "ret", alternate = {"code", "retCode"})
    int ret;
    String msg;
    T data;

}