package com.zhuj.android.http;// package com.zhuj.code.http;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.zhuj.android.http.data.ResponseParser;
import com.zhuj.android.http.request.HttpHeaders;
import com.zhuj.android.http.request.HttpParams;
import com.zhuj.android.http.request.RequestFilter;
import com.zhuj.android.http.request.method.DeleteRequest;
import com.zhuj.code.common.TextUtils;
import com.zhuj.android.util.Exceptions;
import com.zhuj.code.http.request.GetRequest;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Httper {
    private final String TAG = Httper.class.getSimpleName();

    private volatile static Httper instance = null;
    private static Application context;

    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 15000;     //默认的超时时间
    public static final int DEFAULT_RETRY_COUNT = 0;                  //默认重试次数
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 0;         //默认重试叠加时间
    public static final int DEFAULT_RETRY_DELAY = 500;                //默认重试延时
    public static final int DEFAULT_CACHE_NEVER_EXPIRE = -1;

    //======url地址=====//
    private String mBaseUrl;                                          //全局BaseUrl
    private String mSubUrl = "";                                      //全局SubUrl,介于BaseUrl和请求url之间

    //======全局请求头、参数=====//
    private HttpHeaders mCommonHeaders;                               //全局公共请求头
    private HttpParams mCommonParams;                                 //全局公共请求参数

    private OkHttpClient.Builder mOkHttpClientBuilder;                //okHttp请求的客户端

    protected RequestFilter filter;
    protected ResponseParser parser;

    protected String host;
    protected String service;
    protected String method;
    protected Map<String, String> params;
    protected String json;
    protected boolean isAsync;

    /**
     * 获取XHttp实例
     *
     * @return
     */
    public static Httper getInstance() {
        if (instance == null) {
            synchronized (Httper.class) {
                if (instance == null) {
                    instance = new Httper();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    private Httper() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();
//        mOkHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        mOkHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
//        mRetrofitBuilder = new Retrofit.Builder();
//        mRxCacheBuilder = new RxCache.Builder().init(sContext)
//                .diskConverter(new SerializableDiskConverter());      //目前只支持Serializable和Gson缓存其它可以自己扩展
    }
    //==================获取Request请求=====================//

    /**
     * @return get请求
     */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }

    /**
     * @return post请求
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /**
     * @return delete请求
     */
    public static DeleteRequest delete(String url) {
        return new DeleteRequest(url);
    }

    /**
     * @return put请求
     */
    public static PutRequest put(String url) {
        return new PutRequest(url);
    }

    /**
     * @return 自定义请求
     */
    public static CustomRequest custom() {
        return new CustomRequest()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    /**
     * @return 自定义请求
     */
    public static <T> T custom(final Class<T> service) {
        return new CustomRequest()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build()
                .create(service);
    }

    /**
     * @return 下载请求
     */
    public static DownloadRequest downLoad(String url) {
        return new DownloadRequest(url);
    }

    //==================公共请求参数、请求头=====================//

    /**
     * 添加全局公共请求参数
     */
    public Httper addCommonParams(HttpParams commonParams) {
        if (mCommonParams == null) mCommonParams = new HttpParams();
        mCommonParams.put(commonParams);
        return this;
    }

    /**
     * 添加全局公共请求参数
     */
    public Httper addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.put(commonHeaders);
        return this;
    }

    /**
     * 获取全局公共请求参数
     */
    public static HttpParams getCommonParams() {
        return getInstance().mCommonParams;
    }

    /**
     * 获取全局公共请求头
     */
    public static HttpHeaders getCommonHeaders() {
        return getInstance().mCommonHeaders;
    }

    /**
     * 设置接口查询参数，此方法是唯一一个可以多次调用并累加参数的操作
     *
     * @param name  参数名字
     * @param value 值
     * @return this
     */
    public Httper param(String name, String value) {
        if (TextUtils.isEmptyTrim(name)) {
            Exceptions.illegalArgument("name is null or length = 0");
        } else if (value == null) {
            Exceptions.illegalArgument("value is null");
        } else {
            this.params.put(name, value);
        }
        return this;
    }
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    /**
     * 设置结果解析器，仅当不是JSON返回格式时才需要设置
     *
     * @param parser 结果解析器
     * @return Httper
     */
    public Httper parser(ResponseParser parser) {
        if (parser == null) {
            Exceptions.illegalArgument("parser is null");
        }
        this.parser = parser;
        return this;
    }

    /**
     * @param filter 结果解析器
     * @return Httper
     */
    protected Httper filter(RequestFilter filter) {
        if (filter == null) {
            Exceptions.illegalArgument("filter is null");
        }
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
            doPost(service, params);
        }
    }

    protected void postJson(String service, String json) {
        RequestBody requestBody = RequestBody.create(json, MEDIA_TYPE_JSON);
        Request request = new Request.Builder().url(host).post(requestBody).build();
        call(request);
    }

    protected void doPost(String service, Map<String, String> kvMap) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder().host(host);
        if (TextUtils.isEmptyTrim(service)) {
            urlBuilder.addPathSegments('/' == service.charAt(0) ? service.substring(1) : service);
        }
        FormBody formBody = buildFormBody(kvMap);
        Request request = new Request.Builder().url(urlBuilder.build()).post(formBody).build();
        call(request);
    }

    protected void doGet() {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder().host(host);
        if (TextUtil.isEmptyTrim(service)) {
            urlBuilder.addPathSegments('/' == service.charAt(0) ? service.substring(1) : service)
                    .query(buildParams(params));
        }
        Request request = new Request.Builder().url(urlBuilder.build()).build();
        call(request);
    }

    public FormBody buildFormBody(Map<String, String> kvMap) {
        FormBody.Builder builder = new FormBody.Builder();
        if (kvMap != null && !kvMap.isEmpty()) {
            for (Map.Entry<String, String> entry : kvMap.entrySet()) {
                if (entry.getKey() != null) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return builder.build();
    }

    public String buildParams(Map<String, String> kvMap) {
        if (kvMap != null && !kvMap.isEmpty()) {
            StringBuffer ret = new StringBuffer();
            for (Map.Entry<String, String> entry : kvMap.entrySet()) {
                if (entry.getKey() != null) {
                    ret.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            if (ret.length() > 0) {
                return ret.deleteCharAt(0).toString();
            }
        }
        return "";
    }

    public RequestBody buildMultipartBody(Map<String, String> kvMap, Map<String, File> files) {
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (kvMap != null && !kvMap.isEmpty()) {
            for (Map.Entry<String, String> entry : kvMap.entrySet()) {
                if (entry.getKey() != null) {
                    multiBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }
        if (files != null && !files.isEmpty()) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                if (entry.getKey() != null && entry.getValue().exists()) {
                    multiBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(),
                            MultipartBody.create(entry.getValue(), MEDIA_TYPE_STREAM));
                }
            }
        }
        return multiBuilder.build();
    }

    public Request buildRequest(String url, String method, Map<String, String> kvMap) {
        if (TextUtils.isEmpty(url)) Exceptions.illegalArgument("url is null");
        if (TextUtils.isEmptyTrim(method)) Exceptions.illegalArgument("method is null");
        if (method.equalsIgnoreCase("POST")) {
            FormBody formBody = buildFormBody(kvMap);
            return new Request.Builder().url(url).post(formBody).build();
        } else if (method.equalsIgnoreCase("GET")) {
            HttpUrl httpUrl = HttpUrl.get(url + "?" + buildParams(kvMap));
            return new Request.Builder().url(httpUrl).build();
        }
        Exceptions.illegalArgument("method:%s is not implement", method);
        return null;
    }

    protected void call(Request request) {
        Log.d(TAG, JsonUtil.toJson(request));
        if (callback == null) {
            doCallNoCallback(request);
        } else {
            doCall(request);
        }
    }

    public void asyncCall(Request request, Callback callback) {
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    public void syncCall(Request request, Callback callback) {
        Call call = new OkHttpClient().newCall(request);
        try (Response response = call.execute()) {
            callback.onResponse(call, response);
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

}
