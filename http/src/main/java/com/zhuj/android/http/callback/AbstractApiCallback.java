package com.zhuj.android.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.zhuj.android.http.response.ApiResponse;
import com.zhuj.android.http.response.ApiResponseParser;
import com.zhuj.android.http.response.ResponseParser;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 默认是在主线程内处理回调
 */
public abstract class AbstractApiCallback implements Callback {
    procted String TAG = "AbstractApiCallback";

    private ResponseParser apiResponseParser = new ApiResponseParser();

    protected boolean isMainThread;
    protected Handler mDelivery;

    public AbstractApiCallback() {
        this(true);
    }

    public void setMainThread(boolean mainThread) {
        isMainThread = mainThread;
    }

    /**
     * @param isMainThread true表示切到主线程再回调子类方法，
     */
    public AbstractApiCallback(boolean isMainThread) {
        this.isMainThread = isMainThread;
        if (isMainThread) {
            mDelivery = new Handler(Looper.getMainLooper());
        }
    }

    public abstract void onSuccess(ApiResponse result);

    public abstract void onError(Call call, Exception e);

    protected void successData(final ApiResponse apiResponse) {
        if (isMainThread) {
            mDelivery.post(() -> onSuccess(apiResponse));
        } else {
            onSuccess(apiResponse);
        }
    }

    protected void errorData(final Call call, final Exception e) {
        if (isMainThread) {
            mDelivery.post(() -> onError(call, e));
        } else {
            onError(call, e);
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            try {
                successData((ApiResponse) apiResponseParser.parse(response));
            } catch (Exception e) {
                errorData(call, new Exception("数据解析异常"));
            }
        } else {
            errorData(call, new Exception("服务器请求异常"));
        }
    }

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
}
