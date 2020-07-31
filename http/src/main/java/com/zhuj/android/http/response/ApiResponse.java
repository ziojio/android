package com.zhuj.android.http.response;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApiResponse {

    //    @SerializedName(value = "ret", alternate = {"code", "retCode"})
    int ret;
    String msg;
    String data;

    public ApiResponse(int ret, String data) {
        this.ret = ret;
        this.data = data;
    }

}