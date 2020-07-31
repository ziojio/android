package com.zhuj.android.http.interceptor;

import com.zhuj.android.http.response.Response;

import java.io.IOException;

public interface Interceptor {

    /**
     *
     * @param chain request chain.
     * @return the server connection.
     * @throws IOException io exception may occur during the implementation, you can handle or throw.
     */
   Response intercept(Chain chain) throws IOException;
}