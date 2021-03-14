package zhuj.http.callback;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import zhuj.http.util.ZDebug;

public abstract class StringCallback implements okhttp3.Callback {
    protected final String TAG = "StringCallback";

    @Override
    public void onResponse(@NonNull Call call, Response response) {
        if (response.isSuccessful()) {
            try {
                String bodyStr = response.body().string();
                ZDebug.debug(TAG, "result=" + bodyStr);
                onSuccess(bodyStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ZDebug.error(TAG, "code=" + response.code() + ", msg=" + response.message());
            onFailure(call, new IOException(response.message()));
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        String url = call != null ? call.request().url().toString() : "null";
        ZDebug.error(TAG, "Call: " + url + ", msg: " + e.getMessage());
    }

    public abstract void onSuccess(String result);
}