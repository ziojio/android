package com.zhuj.android.http.interceptor;

import com.zhuj.android.http.request.Request;
import com.zhuj.android.http.response.Response;

import java.io.IOException;


public interface Chain {
    /**
     * Get the request in the chain.
     *
     * @return target request.
     */
    Request request();

    /**
     * Proceed to the next request processing.
     *
     * @param request target request.
     * @return {@link Response}.
     * @throws IOException io exception may occur during the implementation, you can handle or throw.
     */
    Response proceed(Request request) throws IOException;

    /**
     * Return {@link Call}, request will be executed on it.
     *
     * @return {@link Call}.
     */
    // Call call();
}