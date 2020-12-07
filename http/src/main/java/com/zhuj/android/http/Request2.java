package com.zhuj.android.http;

import android.util.Log;

import com.zhuj.android.http.response.ResponseParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Request2 {
    String mMethod;
    //====请求地址=====//
    HttpUrl mHttpUrl;
    String mBaseUrl;                                              //baseUrl
    String mSubUrl;                                                 //SubUrl,介于BaseUrl和请求url之间
    String mUrl;                                                  //请求url

    //  private final Proxy mProxy;
    private SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;

    private int mConnectTimeout;
    private int mReadTimeout;
    private Object mTag;

    //====请求行为=====//
    boolean mIsSyncRequest = false;                                //是否是同步请求
    boolean mIsOnMainThread = true;                                //响应是否回到主线程
    boolean mKeepJson = false;                                    //是否返回原始的json格式

    //====请求校验=====//
    private boolean mSign = false;                                          //是否需要签名
    private boolean mTimeStamp = false;                                     //是否需要追加时间戳
    private boolean mAccessToken = false;                                   //是否需要追加token

    //====请求超时重试=====//
    long mReadTimeOut;                                            //读超时
    long mWriteTimeOut;                                           //写超时
    int mRetryCount;                                              //重试次数默认3次
    int mRetryDelay;                                              //延迟xxms重试
    int mRetryIncreaseDelay;                                      //叠加延迟

    //====请求头，公共参数的设置=====//
    HttpHeaders mHeaders = new HttpHeaders();                     //添加的header
    HttpParams mParams = new HttpParams();                        //添加的param

    //====请求缓存=====//
//     protected RxCache mRxCache;                                             //rxCache缓存
//     protected Cache mCache;
    //     protected CacheMode mCacheMode;                                         //默认无缓存
    // protected long mCacheTime;                                              //缓存时间
    // protected String mCacheKey;                                             //缓存Key
    //     protected IDiskConverter mDiskConverter;                                //设置RxCache磁盘转换器
    //====OkHttpClient的拦截器、代理等=====//

    OkHttpClient mOkHttpClient;
    ResponseParser parser;                                                     // 默认结果解析
    ApiService apiService;
    // protected Proxy mProxy;
    // protected final List<Interceptor> mNetworkInterceptors = new ArrayList<>();
    // protected final List<Interceptor> mInterceptors = new ArrayList<>();
    //====Retrofit的Api、Factory=====//
//     protected Retrofit mRetrofit;
//     protected ApiService mApiManager;                                       //通用的的api接口
//     protected List<Converter.Factory> mConverterFactories = new ArrayList<>();
//     protected List<CallAdapter.Factory> mAdapterFactories = new ArrayList<>();
    //====Https设置=====//
//     protected HttpsObjects.SSLParams mSSLParams;
    //====Cookie设置=====//
    // protected List<Cookie> mCookies = new ArrayList<>();                    //用户手动添加的Cookie

    protected Request2(Httper2 httper2) {
        this.mBaseUrl = httper2.mBaseUrl;
        this.mSubUrl = httper2.mSubUrl;
        this.mHeaders.add(httper2.mCommonHeaders);
        this.mParams.put(httper2.mCommonParams);
        this.mOkHttpClient = httper2.mOkHttpClient;
        this.parser = httper2.parser;
//         this.mProxy = builder.mProxy;
//         this.mSSLSocketFactory = httper2.mSSLSocketFactory;
//         this.mHostnameVerifier = httper2.mHostnameVerifier;
//         this.mConnectTimeout = httper2.mConnectTimeout;
//         this.mReadTimeout = httper2.mReadTimeout;
    }

    public <T> void callback(com.zhuj.android.http.callback.Callback<T> callback) {

        Call call = mOkHttpClient.newCall(getRequest());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("EEE", call.request().url() + ", msg=" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("EEE", "" + response.isSuccessful());
                callback.onSuccess((T) parser.parse(response));
            }
        });
    }

    private Request getRequest() {
        Request.Builder builder = new Request.Builder();
        // for (Map.Entry<String, List<String>> item : this.mHeaders.toMap().entrySet()) {
        //     for (String str : item.getValue()) {
        //         builder.addHeader(item.getKey(), str);
        //     }
        // }
        builder.url(this.mUrl.startsWith("http")
                ? mUrl : mBaseUrl + "/" + mUrl);
       // builder.post(new FormBody.Builder().build());
        return builder.build();
    }

    public Request2 ResponseParser(ResponseParser parser) {
        this.parser = parser;
        return this;
    }

    public Request2 url(String url) {
        mUrl = url;
        return this;
    }

    public Request2 method(String method) {
        mMethod = method;
        return this;
    }

    /**
     * Get method.
     */
    public String method() {
        return mMethod;
    }

    public OkHttpClient okHttpClient() {
        return mOkHttpClient;
    }

    public Request2 okHttpClient(OkHttpClient newClient) {
        mOkHttpClient = newClient;
        return this;
    }

    public Request2 syncRequest(boolean syncRequest) {
        mIsSyncRequest = syncRequest;
        return this;
    }

    public boolean isSyncRequest() {
        return mIsSyncRequest;
    }

    /**
     * Get HttpHeaders.
     */
    public HttpHeaders HttpHeaders() {
        return mHeaders;
    }


    /**
     * Get SSLSocketFactory.
     */
    public SSLSocketFactory sslSocketFactory() {
        return mSSLSocketFactory;
    }

    /**
     * Get the HostnameVerifier.
     */
    public HostnameVerifier hostnameVerifier() {
        return mHostnameVerifier;
    }

    /**
     * Get the connection timeout time, Unit is a millisecond.
     */
    public int connectTimeout() {
        return mConnectTimeout;
    }

    /**
     * Get the readResponse timeout time, Unit is a millisecond.
     */
    public int readTimeout() {
        return mReadTimeout;
    }

    /**
     * Get tag.
     */
    public Object tag() {
        return mTag;
    }

}