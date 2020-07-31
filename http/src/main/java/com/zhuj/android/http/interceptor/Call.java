package com.zhuj.android.http.interceptor;

import com.zhuj.android.http.request.Request;
import com.zhuj.android.http.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.internal.connection.ConnectInterceptor;

public class Call {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

    private final Request mRequest;
    private ConnectInterceptor mConnectInterceptor;
    private boolean isExecuted;
    private boolean isCanceled;

    public Call(Request request) {
        this.mRequest = request;
    }

    /**
     * Execute request.
     */
    public Response execute() throws IOException {
        if (isCanceled) throw new CancellationException("The request has been cancelled.");
        isExecuted = true;

        List<Interceptor> interceptors = new ArrayList<>(Kalle.getConfig().getInterceptor());
        mConnectInterceptor = new ConnectInterceptor();
        interceptors.add(mConnectInterceptor);

        Chain chain = new AppChain(interceptors, 0, mRequest, this);
        try {
            return chain.proceed(mRequest);
        } catch (Exception e) {
            if (isCanceled) {
                throw new CancellationException("The request has been cancelled.");
            } else {
                throw e;
            }
        }
    }

    /**
     * The call is executed.
     *
     * @return true, otherwise is false.
     */
    public boolean isExecuted() {
        return isExecuted;
    }

    /**
     * The call is canceled.
     *
     * @return true, otherwise is false.
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * Cancel the call.
     */
    public void cancel() {
        if (!isCanceled) {
            isCanceled = true;
            if (mConnectInterceptor != null) {
                mConnectInterceptor.cancel();
            }
        }
    }

    /**
     * Cancel the call asynchronously.
     */
    public void asyncCancel() {
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                cancel();
            }
        });
    }
}