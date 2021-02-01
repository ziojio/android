package zhuj.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import zhuj.json.GsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class TypeCallback<T> implements Callback {
    protected final String TAG = getClass().getSimpleName();
    protected boolean isMainThread;
    protected Handler mDelivery;
    protected Type type;

    public TypeCallback() {
        this.type = new TypeToken<T>() {
        }.getType();
    }

    public void setMainThread(boolean mainThread) {
        isMainThread = mainThread;
        if (isMainThread) {
            mDelivery = new Handler(Looper.getMainLooper());
        } else {
            mDelivery = null;
        }
    }

    public TypeCallback(boolean mainThreadCallback) {
        this();
        setMainThread(mainThreadCallback);
    }

    public T parseResponse(String body) {
        return GsonUtils.fromJson(body, type);
    }


    public abstract void onSuccess(T result);

    public abstract void onError(T error);


    protected void successData(final T parserResult) {
        if (isMainThread) {
            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    TypeCallback.this.onSuccess(parserResult);
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
            try {
                String body = response.body().string();
                T t = parseResponse(body);
                successData(t);
            } catch (Exception e) {
                errorData(call, e);
            }
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
