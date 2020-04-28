// package com.zhuj.android.http;
//
// import android.util.Log;
//
// import com.zhuj.android.http.ErrorMessage;
// import com.zhuj.android.common.Exceptions;
// import com.zhuj.android.common.TextUtil;
//
// import java.io.File;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
//
// import okhttp3.Call;
// import okhttp3.FormBody;
// import okhttp3.HttpUrl;
// import okhttp3.MediaType;
// import okhttp3.MultipartBody;
// import okhttp3.OkHttpClient;
// import okhttp3.Request;
// import okhttp3.RequestBody;
// import okhttp3.Response;
//
// public class Httper {
//     private final String TAG = Httper.class.getSimpleName();
//
//     public static MediaType MEDIA_TYPE_JSON = MediaType.Companion.parse("application/json; charset=utf-8");
//     public static MediaType MEDIA_TYPE_MARKDOWN = MediaType.Companion.parse("text/x-markdown; charset=utf-8");
//     public static MediaType MEDIA_TYPE_STREAM = MediaType.Companion.parse("application/octet-stream");
//
//     protected  RequestFilter filter;
//     protected  ResponseParser parser;
//     protected com.jbzh.jbim.http.HttpCallback callback;
//
//     protected String host;
//     protected String service;
//     protected String method;
//     protected Map<String, String> params;
//     protected String json;
//     protected boolean isAsync;
//
//     /**
//      * 创建一个接口实例，不是单例模式
//      */
//     public Httper() {
//         host = "http://localhost/";
//         reset();
//     }
//
//     /**
//      * 重置，将接口服务名称、接口参数、便于重复请求
//      *
//      * @return Httper
//      */
//     public Httper reset() {
//         service = "";
//         method = "GET";
//         params = new HashMap<>();
//         json = "";
//         isAsync = true;
//         return this;
//     }
//
//     /**
//      * 设置接口域名 http/https
//      *
//      * @param host base url
//      * @return Httper
//      */
//     public Httper host(String host) {
//         if (TextUtil.isEmptyTrim(host)) {
//             Exceptions.illegalArgument("host is null or length = 0");
//         } else if (!host.startsWith("http")) {
//             Exceptions.illegalArgument("host must start http / https");
//         }
//         this.host = host;
//         return this;
//     }
//
//     public boolean checkHost(String host) {
//         if (TextUtil.isEmptyTrim(host)) {
//             Exceptions.illegalArgument("host is null or length = 0");
//         } else if (!host.startsWith("http")) {
//             Exceptions.illegalArgument("host must start http / https");
//         }
//         return true;
//     }
//
//     /**
//      * 设置将在调用的接口服务名称，如：Default.Index
//      *
//      * @param apiName 接口服务名称
//      * @return Httper
//      */
//     public Httper service(String apiName) {
//         if (TextUtil.isEmptyTrim(apiName)) {
//             Log.w(TAG, "apiName is null or length = 0");
//         } else {
//             this.service = apiName.trim();
//         }
//         return this;
//     }
//
//     /**
//      * 设置为异步请求
//      *
//      * @return Httper
//      */
//     public Httper async() {
//         isAsync = true;
//         return this;
//     }
//
//     /**
//      * 设置为同步请求
//      *
//      * @return Httper
//      */
//     public Httper sync() {
//         isAsync = false;
//         return this;
//     }
//
//     /**
//      * 设置请求为 get
//      *
//      * @return Httper
//      */
//     public Httper get() {
//         method = "GET";
//         return this;
//     }
//
//     /**
//      * 设置请求为 post
//      *
//      * @return Httper
//      */
//     public Httper post() {
//         method = "POST";
//         return this;
//     }
//
//     /**
//      * 设置接口查询参数，此方法是唯一一个可以多次调用并累加参数的操作
//      *
//      * @param name  参数名字
//      * @param value 值
//      * @return this
//      */
//     public Httper param(String name, String value) {
//         if (TextUtil.isEmptyTrim(name)) {
//             Exceptions.illegalArgument("name is null or length = 0");
//         } else if (value == null) {
//             Exceptions.illegalArgument("value is null");
//         } else {
//             this.params.put(name, value);
//         }
//         return this;
//     }
//
//     /**
//      * 设置接口查询参数
//      *
//      * @param kvMap kvMap查询参数
//      * @return Httper
//      */
//     public Httper param(Map<String, String> kvMap) {
//         if (kvMap == null) {
//             Exceptions.illegalArgument("kvMap is null");
//         } else {
//             params.putAll(kvMap);
//         }
//         return this;
//     }
//
//     /**
//      * 设置接口 json body
//      *
//      * @param json json String
//      * @return Httper
//      */
//     public Httper json(String json) {
//         if (TextUtil.isEmptyTrim(json)) {
//             Exceptions.illegalArgument("json is null or length = 0");
//         } else if (json.startsWith("[{") || json.startsWith("{")) {
//             Exceptions.illegalArgument("json must start { or [{");
//         }
//         this.json = json;
//         return this;
//     }
//
//
//     /**
//      * 对请求结果进行处理
//      *
//      * @param httpCallback 回调函数
//      * @return this
//      */
//     public Httper callback( com.jbzh.jbim.http.HttpCallback httpCallback) {
//         if (httpCallback == null) {
//             Exceptions.illegalArgument("callback is null");
//         }
//         this.callback = httpCallback;
//         return this;
//     }
//
//     /**
//      * 设置结果解析器，仅当不是JSON返回格式时才需要设置
//      *
//      * @param parser 结果解析器
//      * @return Httper
//      */
//     public Httper parser( ResponseParser parser) {
//         if (parser == null) {
//             Exceptions.illegalArgument("parser is null");
//         }
//         this.parser = parser;
//         return this;
//     }
//
//     /**
//      * @param filter 结果解析器
//      * @return Httper
//      */
//     protected Httper filter( RequestFilter filter) {
//         if (filter == null) {
//             Exceptions.illegalArgument("filter is null");
//         }
//         this.filter = filter;
//         return this;
//     }
//
//     /**
//      * 发起接口请求
//      */
//     public void request() {
//         if (method.equals("GET")) {
//             doGet();
//         } else if (method.equals("POST")) {
//             doPost(service, params);
//         }
//     }
//
//     protected void postJson(String service, String json) {
//         RequestBody requestBody = RequestBody.Companion.create(json, MEDIA_TYPE_JSON);
//         Request request = new Request.Builder().url(host).post(requestBody).build();
//         call(request);
//     }
//
//     protected void doPost(String service, Map<String, String> kvMap) {
//         HttpUrl.Builder urlBuilder = new HttpUrl.Builder().host(host);
//         if (TextUtil.isEmptyTrim(service)) {
//             urlBuilder.addPathSegments('/' == service.charAt(0) ? service.substring(1) : service);
//         }
//         FormBody formBody = buildFormBody(kvMap);
//         Request request = new Request.Builder().url(urlBuilder.build()).post(formBody).build();
//         call(request);
//     }
//
//     protected void doGet() {
//         HttpUrl.Builder urlBuilder = new HttpUrl.Builder().host(host);
//         if (TextUtil.isEmptyTrim(service)) {
//             urlBuilder.addPathSegments('/' == service.charAt(0) ? service.substring(1) : service)
//                     .query(buildParams(params));
//         }
//         Request request = new Request.Builder().url(urlBuilder.build()).build();
//         call(request);
//     }
//
//     public FormBody buildFormBody(Map<String, String> kvMap) {
//         FormBody.Builder builder = new FormBody.Builder();
//         if (kvMap != null && !kvMap.isEmpty()) {
//             for (Map.Entry<String, String> entry : kvMap.entrySet()) {
//                 if (entry.getKey() != null) {
//                     builder.add(entry.getKey(), entry.getValue());
//                 }
//             }
//         }
//         return builder.build();
//     }
//
//     public String buildParams(Map<String, String> kvMap) {
//         if (kvMap != null && !kvMap.isEmpty()) {
//             StringBuffer ret = new StringBuffer();
//             for (Map.Entry<String, String> entry : kvMap.entrySet()) {
//                 if (entry.getKey() != null) {
//                     ret.append("&").append(entry.getKey()).append("=").append(entry.getValue());
//                 }
//             }
//             if (ret.length() > 0) {
//                 return ret.deleteCharAt(0).toString();
//             }
//         }
//         return "";
//     }
//
//     public RequestBody buildMultipartBody(Map<String, String> kvMap, Map<String, File> files) {
//         MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//         if (kvMap != null && !kvMap.isEmpty()) {
//             for (Map.Entry<String, String> entry : kvMap.entrySet()) {
//                 if (entry.getKey() != null) {
//                     multiBuilder.addFormDataPart(entry.getKey(), entry.getValue());
//                 }
//             }
//         }
//         if (files != null && !files.isEmpty()) {
//             for (Map.Entry<String, File> entry : files.entrySet()) {
//                 if (entry.getKey() != null && entry.getValue().exists()) {
//                     multiBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(),
//                             MultipartBody.create(entry.getValue(), MEDIA_TYPE_STREAM));
//                 }
//             }
//         }
//         return multiBuilder.build();
//     }
//
//     public Request buildRequest(String url, String method, Map<String, String> kvMap) {
//         if (TextUtil.isEmptyTrim(url)) Exceptions.illegalArgument("url is null");
//         if (TextUtil.isEmptyTrim(method)) Exceptions.illegalArgument("method is null");
//         if (method.equalsIgnoreCase("POST")) {
//             FormBody formBody = buildFormBody(kvMap);
//             return new Request.Builder().url(url).post(formBody).build();
//         } else if (method.equalsIgnoreCase("GET")) {
//             HttpUrl httpUrl = HttpUrl.get(url + "?" + buildParams(kvMap));
//             return new Request.Builder().url(httpUrl).build();
//         }
//         Exceptions.illegalArgument("method:%s is not implement", method);
//         return null;
//     }
//
//     protected void call(Request request) {
//         Log.d(TAG, JsonUtil.toJson(request));
//         if (callback == null) {
//             doCallNoCallback(request);
//         } else {
//             doCall(request);
//         }
//     }
//
//     public void asyncCall(Request request, Callback callback) {
//         new OkHttpClient().newCall(request).enqueue(callback);
//     }
//
//     public void syncCall(Request request, Callback callback) {
//         Call call = new OkHttpClient().newCall(request);
//         try (Response response = call.execute()) {
//             callback.onResponse(call, response);
//         } catch (IOException e) {
//             callback.onFailure(call, e);
//         }
//     }
//
//     protected void doCall(Request request) {
//         if (isAsync) {
//             new OkHttpClient().newCall(request).enqueue(okhttpCallback);
//         } else {
//             Call call = new OkHttpClient().newCall(request);
//             try (Response response = call.execute()) {
//                 okhttpCallback.onResponse(call, response);
//             } catch (IOException e) {
//                 okhttpCallback.onFailure(call, e);
//             }
//         }
//     }
//
//     protected Callback okhttpCallback = new Callback() {
//         @Override
//         public void onFailure(Call call, IOException e) {
//             callback.onFailure(new ErrorMessage(e));
//         }
//
//         @Override
//         public void onResponse(Call call, Response response) {
//             if (response.isSuccessful()) {
//                 callback.onSuccess(parser.parse(response));
//             } else {
//                 callback.onFailure(new ErrorMessage(response.code(), response.message()));
//             }
//         }
//     };
//
//     protected void doCallNoCallback(Request request) {
//         new OkHttpClient().newCall(request).enqueue(new Callback() {
//             @Override
//             public void onFailure(Call call, IOException e) {
//             }
//
//             @Override
//             public void onResponse(Call call, Response response) {
//             }
//         });
//     }
// }
