package com.zhuj.android.http.data;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Response;


public class ApiResponseParser implements ResponseParser<ApiResponse> {

    public ApiResponse parse(Response response) {
        try {
            String ret = response.body() != null ? response.body().string() : null;
            if (ret != null) {
                JsonObject jsonObj = JsonParser.parseString(ret).getAsJsonObject();
                return new ApiResponse(
                        jsonObj.get("ret").getAsInt(),
                        jsonObj.get("data").getAsString(),
                        jsonObj.get("msg").getAsString());
            } else {
                return new ApiResponse(400, "", "body is null");
            }
        } catch (Exception ex) {
            return new ApiResponse(500, "", "Internal Server Error Or " + ex.getMessage());
        }
    }

}
