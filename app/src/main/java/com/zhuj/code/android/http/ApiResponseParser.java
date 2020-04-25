package com.jbzh.jbim.http;

import org.json.JSONObject;

import okhttp3.Response;


public class ApiResponseParser implements ResponseParser<ApiResponse> {

    public ApiResponse parse(Response response) {
        try {
            String ret = response.body() != null ? response.body().string() : null;
            if (ret != null) {
                JSONObject jsonObj = new JSONObject(ret);
                return new ApiResponse(
                        jsonObj.getInt("ret"),
                        jsonObj.getString("data"),
                        jsonObj.getString("msg"));
            } else {
                return new ApiResponse(400, "", "body is null");
            }
        } catch (Exception ex) {
            return new ApiResponse(500, "", "Internal Server Error Or " + ex.getMessage());
        }
    }

}
