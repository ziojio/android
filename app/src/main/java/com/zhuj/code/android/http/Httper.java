

import android.util.Log;

import com.jbzh.im.util.Exceptions;
import com.jbzh.im.util.JsonUtils;
import com.jbzh.im.util.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @param <T> 解析的结果，和回调处理的对象
 */
public class Httper<T> {
    private final String TAG = Httper.class.getSimpleName();
    protected OkHttpClient okHttpClient;
    protected RequestFilter filter;
    protected ResponseParser<T> parser;
    protected String host;

    protected String service;
    protected String method;
    protected Map<String, String> params;
    protected String json;
    protected boolean isAsync;
    protected HttpCallback<T> callback;

    /**
     * 创建一个接口实例，不是单例模式
     */
    public Httper() {
        defaultHttpClient();
        host = "http://localhost/";
        reset();
    }

    private void defaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // builder = builder.sslSocketFactory(new SSL(trustAllCert), trustAllCert); //忽略安全证书认证
        okHttpClient = builder.connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .build();
    }

    public Httper<T> httpClient(OkHttpClient httpClient) {
        okHttpClient = httpClient;
        return this;
    }

    /**
     * 重置，将接口服务名称、接口参数、便于重复请求
     *
     * @return Httper
     */
    public Httper<T> reset() {
        service = "";
        method = "POST";
        params = new HashMap<>();
        json = "";
        isAsync = true;
        return this;
    }

    /**
     * 设置接口域名 http/https
     *
     * @param host base url
     * @return Httper
     */
    public Httper<T> host(String host) {
        if (TextUtils.isEmptyTrim(host)) {
            Exceptions.illegalArgument("host is null or length = 0");
        }
        this.host = host;
        return this;
    }

    /**
     * 设置将在调用的接口服务名称，如：Default.Index
     *
     * @param apiName 接口服务名称
     * @return Httper
     */
    public Httper<T> service(String apiName) {
        if (TextUtils.isEmptyTrim(apiName)) {
            Log.w(TAG, "apiName is null or length = 0");
        } else {
            this.service = apiName.trim();
        }
        return this;
    }

    /**
     * 设置为异步请求
     *
     * @return Httper
     */
    public Httper<T> async() {
        isAsync = true;
        return this;
    }

    /**
     * 设置为同步请求
     *
     * @return Httper
     */
    public Httper<T> sync() {
        isAsync = false;
        return this;
    }

    /**
     * 设置请求为 get
     *
     * @return Httper
     */
    public Httper<T> get() {
        method = "GET";
        return this;
    }

    /**
     * 设置请求为 post
     *
     * @return Httper
     */
    public Httper<T> post() {
        method = "POST";
        return this;
    }

    /**
     * 设置接口查询参数，此方法是唯一一个可以多次调用并累加参数的操作
     *
     * @param name  参数名字
     * @param value 值
     * @return this
     */
    public Httper<T> param(String name, String value) {
        if (TextUtils.isEmptyTrim(name)) {
            Exceptions.illegalArgument("name is null or length = 0");
        } else if (value == null) {
            Exceptions.illegalArgument("value is null");
        } else {
            this.params.put(name, value);
        }
        return this;
    }

    /**
     * 设置接口 json body
     *
     * @param json json String
     * @return Httper
     */
    public Httper<T> json(String json) {
        if (TextUtils.isEmptyTrim(json)) {
            Exceptions.illegalArgument("json is null or length = 0");
        } else if (json.startsWith("[{") || json.startsWith("{")) {
            Exceptions.illegalArgument("json must start { or [{");
        } else {
            this.json = json;
        }
        return this;
    }


    /**
     * 对请求结果进行处理
     *
     * @param httpCallback 回调函数
     * @return this
     */
    public Httper<T> callback(HttpCallback<T> httpCallback) {
        if (httpCallback == null) {
            Exceptions.illegalArgument("callback not is null");
        }
        this.callback = httpCallback;
        return this;
    }

    /**
     * 设置结果解析器，仅当不是JSON返回格式时才需要设置
     *
     * @param parser 结果解析器
     * @return Httper
     */
    public Httper<T> parser(ResponseParser<T> parser) {
        this.parser = parser;
        return this;
    }

    /**
     * @param filter 结果解析器
     * @return Httper
     */
    public Httper<T> filter(RequestFilter filter) {
        this.filter = filter;
        return this;
    }

    /**
     * 发起接口请求
     */
    public void request() {
        if (method.equals("GET")) {
            doGet();
        } else if (method.equals("POST")) {
            doPost();
        }
    }

    private void doPost() {
        Request request = new Request.Builder().url(buildHttpUrlWithService().build()).post(buildRequestBody()).build();
        call(request);
    }

    private void doGet() {
        HttpUrl.Builder urlBuilder = buildHttpUrlWithService();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        Request request = new Request.Builder().url(urlBuilder.build()).build();
        call(request);
    }

    private HttpUrl.Builder buildHttpUrlWithService() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(host).newBuilder();
        if (service.length() > 0) {
            urlBuilder.addQueryParameter("s", service);
        }
        return urlBuilder;
    }

    private RequestBody buildRequestBody() {
        RequestBody requestBody = null;
        if (json.length() > 0) {
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        } else if (!params.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            requestBody = builder.build();
        }
        return requestBody;
    }

    private String buildParams() {
        StringBuilder ret = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ret.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return ret.toString();
    }

    private RequestBody buildMultipartBody() {
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // 参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            multiBuilder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        // 需要上传的文件，需要携带上传的文件
        HashMap<String, File> files = new HashMap<>();
        for (String key : files.keySet()) {
            multiBuilder.addFormDataPart(key, files.get(key).getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), files.get(key)));
        }
        return multiBuilder.build();
    }

    private Callback okhttpCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            callback.onFailure(new ErrorMessage(e));
        }

        @Override
        public void onResponse(Call call, Response response) {
            if (response.isSuccessful()) {
                callback.onSuccess(parser.parse(response));
            } else {
                callback.onFailure(new ErrorMessage(response.code(), response.message()));
            }
        }
    };

    private void call(Request request) {
        Log.d(TAG, JsonUtils.toJson(request));
//        if(callback == null){
//            doCallNoCallback(request);
//            return;
//        }
        if (isAsync) {
            okHttpClient.newCall(request).enqueue(okhttpCallback);
        } else {
            try (Response response = okHttpClient.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    callback.onSuccess(parser.parse(response));
                } else {
                    callback.onFailure(new ErrorMessage(response.code(), response.message()));
                }
            } catch (IOException e) {
                callback.onFailure(new ErrorMessage(e));
            }
        }
    }

    private void doCallNoCallback(Request request) {
        if (isAsync) {
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                }
            });
        } else {
            try (Response ignored = okHttpClient.newCall(request).execute()) {
            } catch (IOException ignored) {
            }
        }

    }
}
