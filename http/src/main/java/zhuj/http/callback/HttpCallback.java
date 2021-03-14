package zhuj.http.callback;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import zhuj.http.util.ZDebug;
import zhuj.java.json.GsonUtils;
import zhuj.java.lang.TypeUtils;

public abstract class HttpCallback<T> implements okhttp3.Callback {
    private static final String TAG = "HttpCallback";

    @Override
    public void onResponse(@NonNull Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String bodyStr = response.body().string();
                ZDebug.debug(false, TAG, bodyStr);
                ApiResult<T> result = GsonUtils.getGson().fromJson(bodyStr,
                        TypeUtils.newParameterizedType(ApiResult.class, TypeUtils.getSuperclassTypeParameter(getClass())));
                ZDebug.debug(true, TAG, GsonUtils.toPrettyJson(result));
                if (result == null) {
                    onFailure(call, new IOException("result is NULL"));
                } else if (!result.isSuccess()) {
                    onFailure(call, new IOException(result.getMsg()));
                } else if (result.getData() == null) {
                    onFailure(call, new IOException("data is NULL"));
                } else {
                    try {
                        onSuccess(result); // 捕获执行出错的异常
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) { // 捕获 type get error
                onFailure(call, new IOException(e));
            }
        } else {
            onFailure(call, new IOException(response.message()));
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        String url = call != null ? call.request().url().toString() : "null";
        ZDebug.debug(true, TAG, "URL: " + url + ", MSG: " + e.getMessage());
    }

    public void onSuccess(ApiResult<T> result) {
        onSuccess(result.getData());
    }

    public abstract void onSuccess(T t);
}