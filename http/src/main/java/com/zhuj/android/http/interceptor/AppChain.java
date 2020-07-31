package com.zhuj.android.http.interceptor;

import com.zhuj.android.http.request.Request;
import com.zhuj.android.http.response.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by Zhenjie Yan on 2018/3/6.
 */
class AppChain implements Chain {

    private final List<Interceptor> mInterceptors;
    private final int mTargetIndex;
    private final Request mRequest;
    private Call mCall;

    AppChain(List<Interceptor> interceptors, int targetIndex, Request request, Call call) {
        this.mInterceptors = interceptors;
        this.mTargetIndex = targetIndex;
        this.mRequest = request;
        this.mCall = call;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public Response proceed(Request request) throws IOException {
        Interceptor interceptor = mInterceptors.get(mTargetIndex);
        Chain chain = new AppChain(mInterceptors, mTargetIndex + 1, request, mCall);
        return interceptor.intercept(chain);
    }

    @Override
    public Call newCall() {
        return mCall;
    }

    @Override
    public Call call() {
        return mCall;
    }
}