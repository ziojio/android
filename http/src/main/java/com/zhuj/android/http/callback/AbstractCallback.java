package com.zhuj.android.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zhuj.android.http.response.ResponseParser;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class AbstractCallback<S, F> implements Callback {
    protected final String TAG = getClass().getSimpleName();
    protected boolean isMainThread;
    protected Handler mDelivery;
    protected ResponseParser<S> responseParser;


    public void setMainThread(boolean mainThread) {
        isMainThread = mainThread;
        if (isMainThread) {
            mDelivery = new Handler(Looper.getMainLooper());
        } else {
            mDelivery = null;
        }
    }

    /**
     * @param isMainThread true表示切到主线程再回调子类方法，
     */
    public AbstractCallback(boolean isMainThread) {
        setMainThread(isMainThread);
    }

    public void setResponseParser(ResponseParser<S> error) {
        responseParser = error;
    }

    public abstract void onSuccess(S result);

    public abstract void onError(F error);


    protected void successData(final S parserResult) {
        if (isMainThread) {
            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    AbstractCallback.this.onSuccess(parserResult);
                }
            });
        } else {
            onSuccess(parserResult);
        }
    }

    protected abstract void errorData(final Call call, final Exception e);

    protected abstract void errorData(final Call call, final Response response);


    @Override
    public void onResponse(@NonNull Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            S s;
            try {
                s = responseParser.parse(response);
            } catch (Exception e) {
                errorData(call, e);
                return;
            }
            successData(s);
        } else {
            errorData(call, response);
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        Log.e(TAG, "onFailure 请求失败; ", e);
        if (e instanceof ConnectException) {
            Log.e(TAG, "ConnectException", e);
        }
        if (e instanceof SocketTimeoutException) {
            Log.e(TAG, "SocketTimeoutException", e);
        }
        errorData(call, e);
    }
}
