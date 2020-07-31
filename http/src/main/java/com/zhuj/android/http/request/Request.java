package com.zhuj.android.http.request;

import com.zhuj.android.http.HttpHeaders;
import com.zhuj.android.http.HttpParams;
import com.zhuj.android.http.HttpUrl;
import com.zhuj.android.http.Httper;
import com.zhuj.android.http.Params;
import com.zhuj.android.http.RequestMethod;
import com.zhuj.android.http.request.body.RequestBody;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public abstract class Request {
    private RequestMethod mMethod;
    //====请求地址=====//
    protected HttpUrl mHttpUrl;
    protected String mBaseUrl;                                              //baseUrl
    private String mSubUrl;                                                 //SubUrl,介于BaseUrl和请求url之间
    protected String mUrl;                                                  //请求url

    private HttpHeaders mHttpHeaders;

    //  private final Proxy mProxy;
    private SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;

    private int mConnectTimeout;
    private int mReadTimeout;
    private Object mTag;

    //====请求行为=====//
    protected boolean mIsSyncRequest = false;                                //是否是同步请求
    protected boolean mIsOnMainThread = true;                                //响应是否回到主线程
    protected boolean mKeepJson = false;                                    //是否返回原始的json格式

    //====请求校验=====//
    private boolean mSign = false;                                          //是否需要签名
    private boolean mTimeStamp = false;                                     //是否需要追加时间戳
    private boolean mAccessToken = false;                                   //是否需要追加token

    //====请求超时重试=====//
    protected long mReadTimeOut;                                            //读超时
    protected long mWriteTimeOut;                                           //写超时
    protected int mRetryCount;                                              //重试次数默认3次
    protected int mRetryDelay;                                              //延迟xxms重试
    protected int mRetryIncreaseDelay;                                      //叠加延迟

    //====请求头，公共参数的设置=====//
    protected HttpHeaders mHeaders = new HttpHeaders();                     //添加的header
    protected HttpParams mParams = new HttpParams();                        //添加的param

    //====请求缓存=====//
//     protected RxCache mRxCache;                                             //rxCache缓存
//     protected Cache mCache;
    //     protected CacheMode mCacheMode;                                         //默认无缓存
    // protected long mCacheTime;                                              //缓存时间
    // protected String mCacheKey;                                             //缓存Key
    //     protected IDiskConverter mDiskConverter;                                //设置RxCache磁盘转换器
    //====OkHttpClient的拦截器、代理等=====//
    protected OkHttpClient mOkHttpClient;
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

    protected Request(Builder builder) {
        this.mMethod = builder.mMethod;
        this.mUrl = builder.mUrl;
        this.mBaseUrl = builder.mBaseUrl;
        this.mSubUrl = builder.mSubUrl;
        this.mHttpHeaders = builder.mHttpHeaders;
        this.mParams = builder.mParams;

//         this.mProxy = builder.mProxy;
        this.mSSLSocketFactory = builder.mSSLSocketFactory;
        this.mHostnameVerifier = builder.mHostnameVerifier;
        this.mConnectTimeout = builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout;
        this.mTag = builder.mTag;

    }

    /**
     * Get HttpUrl.
     */
    public abstract HttpUrl HttpUrl();

    /**
     * Get params.
     */
    public abstract Params copyParams();

    /**
     * Get request body.
     */
    public abstract RequestBody body();

    /**
     * Get method.
     */
    public RequestMethod method() {
        return mMethod;
    }

    /**
     * Get HttpHeaders.
     */
    public HttpHeaders HttpHeaders() {
        return mHttpHeaders;
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

    public static class Builder {
        protected String mBaseUrl;                                    // baseUrl
        protected String mSubUrl;                                     // SubUrl,介于BaseUrl和请求url之间
        protected String mUrl;                                        // 请求url
        protected RequestMethod mMethod;
        protected final HttpHeaders mHttpHeaders = new HttpHeaders();
        protected final HttpParams mParams = new HttpParams();

        private SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;
        private int mConnectTimeout;
        private int mReadTimeout;
        protected Object mTag;

        /**
         * Overlay path.
         */
        public Builder url(String url) {
            mUrl = url;
            return this;
        }

        /**
         * Overlay path.
         */
        public Builder baseUrl(String host) {
            mBaseUrl = host;
            return this;
        }

        /**
         * Overlay path.
         */
        public Builder subUrl(String path) {
            mSubUrl = path;
            return this;
        }

        /**
         * Add a new header.
         */
        public Builder addHeader(String key, String value) {
            mHttpHeaders.add(key, value);
            return this;
        }

        /**
         * If the target key exists, replace it, if not, add.
         */
        public Builder setHeader(String key, String value) {
            mHttpHeaders.set(key, value);
            return this;
        }

        /**
         * Set HttpHeaders.
         */
        public Builder setHttpHeaders(HttpHeaders HttpHeaders) {
            mHttpHeaders.set(HttpHeaders);
            return this;
        }

        /**
         * Remove the key from the information.
         */
        public Builder removeHeader(String key) {
            mHttpHeaders.remove(key);
            return this;
        }

        /**
         * Remove all header.
         */
        public Builder clearHttpHeaders() {
            mHttpHeaders.clear();
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, int value) {
            mParams.put(key, value);
            return this;
        }

        /**
         * Add parameter.
         */
        public Builder param(String key, String value) {
            mParams.put(key, value);
            return this;
        }

        /**
         * Add parameters.
         */
        public Builder param(String key, List<String> value) {
            mParams.put(key, value);
            return this;
        }

        /**
         * Remove parameters.
         */
        public Builder removeParam(String key) {
            mParams.remove(key);
            return this;
        }

        /**
         * Clear parameters.
         */
        public Builder clearParams() {
            mParams.clear();
            return this;
        }

        /**
         * Clear parameters.
         */
        public Builder params(Map<String, Object> params) {
            mParams.put(params);
            return this;
        }

        /**
         * Clear parameters.
         */
        public Builder params(HttpParams params) {
            mParams.put(params);
            return this;
        }

        /**
         * Proxy information for this request.
         */
        //        public Builder proxy(Proxy proxy) {
        //            this.mProxy = proxy;
        //            return  this;
        //        }

        /**
         * SSLSocketFactory for this request.
         */
        public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSSLSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * HostnameVerifier for this request.
         */
        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * Connect timeout for this request.
         */
        public Builder connectTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mConnectTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Read timeout for this request.
         */
        public Builder readTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mReadTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Tag.
         */
        public Builder tag(Object tag) {
            this.mTag = tag;
            return this;
        }
    }
}