package zhuj.http;

import android.os.Environment;

import com.google.gson.JsonObject;
import zhuj.http.callback.Callback;
import zhuj.java.file.FileIO;
import zhuj.java.lang.Strings;
import zhuj.java.json.GsonUtils;

import java.io.File;
import java.io.IOException;
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

    private static String baseUrl = "https://apijbzh.vanpin.com";
    private static String subUrl = "";
    private static Map<String, HttpUrl> URL_MAP = new HashMap<>();

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static Map<String, String> requestMap = new HashMap<>();

    public static void initCommonParams() {
        String json = FileIO.readString(Environment.getExternalStorageDirectory() + "/jbzh/config/.DTOKEN");
        try {
            JsonObject object = GsonUtils.parseJsonObject(json);
            String dtoken = object.get("dtoken").getAsString();
            if (Strings.isNotBlank(dtoken)) {
                requestMap.put("dtoken", dtoken);
            }
        } catch (Exception ignored) {
        }
    }

    public static Map<String, String> getRequestParams(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (requestMap != null && !requestMap.isEmpty()) {
            map.putAll(requestMap);
        }
        return map;
    }

    private static HttpUrl buildHttpUrl(String url) {
        if (url.startsWith("http")) {
            // Logger.d("build url use set url: " + url);
            return Objects.requireNonNull(HttpUrl.parse(url), "Url is NULL or ERROR");
        }
        HttpUrl httpUrl = URL_MAP.get(baseUrl);
        if (httpUrl == null) {
            httpUrl = Objects.requireNonNull(HttpUrl.parse(baseUrl), "Base Url is NULL or ERROR");
            URL_MAP.put(baseUrl, httpUrl);
        }
        HttpUrl.Builder builder = httpUrl.newBuilder();
        if (subUrl != null && !subUrl.isEmpty()) builder.addPathSegments(subUrl);
        builder.addPathSegments(url);
        // Logger.d("build url use base url: " + builder.toString());
        return builder.build();
    }

    private static FormBody buildFormBody(Map<String, String> map) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        return formBuilder.build();
    }

    private static Request getRequest(HttpUrl httpUrl, Map<String, String> map) {
        Request.Builder requestBuilder = new Request.Builder();
        if (map != null) {
            HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            return requestBuilder.url(urlBuilder.build()).build();
        }
        return requestBuilder.url(httpUrl).build();
    }

    private static Request postRequest(HttpUrl httpUrl, Map<String, String> map, Map<String, File> files) {
        Request.Builder requestBuilder = new Request.Builder();
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

    }

    public static <T> void get(String url, Map<String, String> map, Callback<T> callback) {
        HttpUrl httpUrl = buildHttpUrl(url);
        Request request = getRequest(httpUrl, getRequestParams(map));
        call(request, callback);
    }

    public static <T> void post(String url, Map<String, String> map, Callback<T> callback) {
        postFile(url, map, null, callback);
    }

    public static <T> void postFile(String url, Map<String, String> map, Map<String, File> files, Callback<T> callback) {
        HttpUrl httpUrl = buildHttpUrl(url);
        Request request = postRequest(httpUrl, getRequestParams(map), files);
        call(request, callback);
    }

    public static <T> void postJson(String url, String json, Callback<T> callback) {
        if (json == null) {
            throw new NullPointerException("json == null");
        }
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
        HttpUrl httpUrl = buildHttpUrl(url);
        Request request = new Request.Builder().url(httpUrl).post(requestBody).build();
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

}
