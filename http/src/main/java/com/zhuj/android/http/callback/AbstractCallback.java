package com.zhuj.android.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.zhuj.android.http.data.result.Result;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 默认是在主线程内处理回调
 *
 * @param <T>
 */
public abstract class AbstractCallback<T> implements Callback {
    protected String TAG = "HttpRequest";
    protected boolean isMainThread;
    protected Handler mDelivery;

    public AbstractCallback() {
        this(true);
    }

    public void setMainThread(boolean mainThread) {
        isMainThread = mainThread;
    }

    /**
     * @param isMainThread true表示切到主线程再回调子类方法，
     */
    public AbstractCallback(boolean isMainThread) {
        this.isMainThread = isMainThread;
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public abstract void onResponse(T result);

    public abstract void onError(Call call, Exception e);

    abstract T parseResponse(Call call, String body);


    @Override
    public void onFailure(Call call, IOException e) {
        Log.i(TAG, "服务器请求失败", e);
        if (e instanceof ConnectException) {
            Log.i(TAG, "ConnectException", e);
        }
        if (e instanceof SocketTimeoutException) {
            Log.i(TAG, "SocketTimeoutException", e);
        }
        errorData(call, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            try {
                String body = response.body().string();
                successData(parseResponse(call, body));
            } catch (Exception e) {
                errorData(call, new Exception("数据解析异常"));
            }
        } else {
            errorData(call, new Exception("服务器请求异常"));
        }
    }

    protected void successData(final T t) {
        if (isMainThread) {
            mDelivery.post(() -> callOnResponse(t));
        } else {
            callOnResponse(t);
        }
    }


    protected void errorData(final Call call, final Exception e) {
        if (mainThreadCallback) {
            mDelivery.post(() -> onError(call, e));
        } else {
            onError(call, e);
        }
    }
}
