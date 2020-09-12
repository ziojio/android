package com.zhuj.android.http;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final String TAG = "HttpUtils";

    private static final String HOST = "http://apijbzh.vanpin.com";

    private static final String GetAll = "/App/YiBenZhiHui/GetAll";

    public static <T> void GetAll(Callback<T> callback) {
        Map<String, String> map = new HashMap<>();
        post(HOST, GetAll, map, callback);
    }

    private static FormBody buildFormBody(Map<String, String> map) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        return formBuilder.build();
    }

    private static Request buildGetRequest(String url, Map<String, String> map) {
        return buildRequest("GET", url, map, null);
    }

    private static Request buildJsonRequest(String url, String json) {
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private static Request buildRequest(String method, String url, Map<String, String> map, Map<String, File> files) {
        okhttp3.HttpUrl httpUrl = Objects.requireNonNull(okhttp3.HttpUrl.parse(url), "url == NULL or is ERROR");
        Request.Builder requestBuilder = new Request.Builder();
        if ("GET".equalsIgnoreCase(method)) {
            if (map != null) {
                HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
                return requestBuilder.url(urlBuilder.build()).build();
            }
            return requestBuilder.url(httpUrl).build();
        } else if ("POST".equalsIgnoreCase(method)) {
            if (files == null) {
                Objects.requireNonNull(map, "POST map must NOT NULL");
                FormBody.Builder formBuilder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    formBuilder.add(entry.getKey(), entry.getValue());
                }
                return requestBuilder.url(httpUrl).post(formBuilder.build()).build();
            }
            MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String, File> entry : files.entrySet()) {
                File file = entry.getValue();
                multiBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    multiBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            return requestBuilder.url(httpUrl).post(multiBuilder.build()).build();
        } else {
            return null;
        }
    }

    public static <T> void get(String url, Map<String, String> map, Callback<T> callback) {
        Request request = buildGetRequest(url, map);
        call(request, callback);
    }

    public static <T> void postFile(String url, String apiName, Map<String, String> map, Map<String, File> files, Callback<T> callback) {
        Request request = buildRequest("POST", url + apiName, map, files);
        call(request, callback);
    }

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static <T> void postJson(String url, String apiName, String json, Callback<T> callback) {
        if (json == null) {
            throw new NullPointerException("json == null");
        }
        Request request = buildJsonRequest(url + apiName, json);
        call(request, callback);
    }

    public static <T> void post(String url, String apiName, Map<String, String> map, Callback<T> callback) {
        if (map == null) {
            throw new NullPointerException("map == null");
        }
        Request request = buildRequest("POST", url + apiName, map, null);
        call(request, callback);
    }

    public static <T> void call(Request request, final Callback<T> callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static <T> void syncCall(Request request, Callback<T> callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        try (Response response = call.execute()) {
            callback.onResponse(call, response);
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    public abstract static class Callback<T> implements okhttp3.Callback {
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                JSONObject jsonObject;
                try {
                    String bodyStr = Objects.requireNonNull(response.body()).string();
                    Log.d(TAG, bodyStr);
                    jsonObject = JSONObject.parseObject(bodyStr);
                    // Logger.d(TAG, jsonObject.toString(SerializerFeature.PrettyFormat));
                } catch (Exception e) {
                    onFailure(call, new IOException("parser json error: " + e.getMessage()));
                    return;
                }
                if (jsonObject.getIntValue("ret") != 200) {
                    onFailure(call, new IOException(
                            "retCode error, ret=" + jsonObject.getIntValue("ret")
                                    + ", msg=" + jsonObject.getString("msg")));
                    return;
                }
                T result = null;
                String data = jsonObject.getString("data");
                ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
                if (parameterizedType == null) {
                    onFailure(call, new IOException("泛型参数 is NULL"));
                    return;
                }
                // Logger.d(Arrays.toString(parameterizedType.getActualTypeArguments()));
                Class<T> clazz = null;
                try {
                    clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
                } catch (Exception ignored) {
                }
                // Logger.d("clazz: " + clazz);
                if (clazz == null) {
                    result = JSON.parseObject(data, parameterizedType.getActualTypeArguments()[0]);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (clazz.getTypeName().equals(String.class.getTypeName())) {
                        result = (T) data;
                    } else if (clazz.getTypeName().equals(JSONObject.class.getTypeName())) {
                        result = (T) JSON.parseObject(data);
                    } else if (clazz.getTypeName().equals(JSONArray.class.getTypeName())) {
                        result = (T) JSON.parseArray(data);
                    }
                }
                try {
                    onSuccess(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailure(call, new IOException(e));
                }
            } else {
                onFailure(call, new IOException("response code=" + response.code() + ", msg=" + response.message()));
            }
        }

        public abstract void onSuccess(T result);
    }
}

