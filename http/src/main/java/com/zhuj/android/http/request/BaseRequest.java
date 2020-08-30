// package com.zhuj.android.http.request;
//
// import android.content.Context;
// import android.text.TextUtils;
//
// import com.zhuj.android.http.HttpHeaders;
// import com.zhuj.android.http.HttpParams;
// import com.zhuj.android.http.Httper;
//
// import java.net.Proxy;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.concurrent.TimeUnit;
//
// import javax.net.ssl.HostnameVerifier;
//
// import okhttp3.Cache;
// import okhttp3.Cookie;
// import okhttp3.HttpUrl;
// import okhttp3.Interceptor;
// import okhttp3.MediaType;
// import okhttp3.OkHttpClient;
// import okhttp3.ResponseBody;
// import okio.BufferedSource;
//
// public abstract class BaseRequest<R extends BaseRequest<?>>  extends Request{
//     protected Context mContext;
//
//     /**
//      * 构建基础请求
//      *
//      * @param httper 请求的url
//      */
//     public BaseRequest(Builder builder) {
//         super();
//         mContext = httper.getContext();
//         mBaseUrl = httper.getBaseUrl();
//         mSubUrl = httper.getSubUrl();
//         mOkHttpClient = httper.getOkHttpClient();
//         if (!TextUtils.isEmpty(mBaseUrl)) {
//             mHttpUrl = HttpUrl.parse(mBaseUrl);
//         }
//         // mCacheMode = httper.getCacheMode();                                //添加缓存模式
//         // mCacheTime = httper.getCacheTime();                                //缓存时间
//         mRetryCount = httper.getRetryCount();                              //超时重试次数
//         mRetryDelay = httper.getRetryDelay();                              //超时重试延时
//         mRetryIncreaseDelay = httper.getRetryIncreaseDelay();              //超时重试叠加延时
//         //OKHttp  mCache
//         // mCache = httper.getHttpCache();
//         //默认添加 Accept-Language
//         String acceptLanguage = HttpHeaders.getAcceptLanguage();
//         if (!TextUtils.isEmpty(acceptLanguage))
//             headers(HttpHeaders.KEY_ACCEPT_LANGUAGE, acceptLanguage);
//         //默认添加 User-Agent
//         String userAgent = HttpHeaders.getUserAgent(mContext);
//         if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.KEY_USER_AGENT, userAgent);
//         //添加公共请求参数
//         if (httper.getCommonParams() != null) mParams.put(httper.getCommonParams());
//         if (httper.getCommonHeaders() != null) mHeaders.set(httper.getCommonHeaders());
//     }
//
//     //===========================================//
//     //               请求url设置                  //
//     //===========================================//
//
//     /**
//      * 设置url路径
//      *
//      * @param url
//      */
//     public R url(String url) {
//         mUrl = Objects.requireNonNull(url, "mUrl == null");
//         return (R) this;
//     }
//
//     /**
//      * 设置基础url路径
//      *
//      * @param baseUrl
//      */
//     public R baseUrl(String baseUrl) {
//         mBaseUrl = baseUrl;
//         if (!TextUtils.isEmpty(mBaseUrl)) {
//             mHttpUrl = HttpUrl.parse(baseUrl);
//         }
//         return (R) this;
//     }
//
//     /**
//      * 设置基础subUrl路径
//      *
//      * @param subUrl
//      */
//     public R subUrl(String subUrl) {
//         mSubUrl = Objects.requireNonNull(subUrl, "mSubUrl == null");
//         return (R) this;
//     }
//
//     /**
//      * 获取请求的地址
//      */
//     public String getUrl() {
//         return mSubUrl + mUrl;
//     }
//
//     /**
//      * @return 获取基础地址
//      */
//     public String getBaseUrl() {
//         return mBaseUrl;
//     }
//
//     //===========================================//
//     //               请求校验                     //
//     //===========================================//
//
//     /**
//      * 保存json的形式（不返回对象，保持Json的String形式，不过传入的必须是String.class）
//      *
//      * @param keepJson
//      */
//     public R keepJson(boolean keepJson) {
//         mKeepJson = keepJson;
//         return (R) this;
//     }
//
//     /**
//      * 是否是同步请求（不开子线程，默认是false）
//      *
//      * @param syncRequest
//      */
//     public R syncRequest(boolean syncRequest) {
//         mIsSyncRequest = syncRequest;
//         return (R) this;
//     }
//
//     /**
//      * 请求完成后是否回到主线程（默认是true）
//      *
//      * @param onMainThread
//      */
//     public R onMainThread(boolean onMainThread) {
//         mIsOnMainThread = onMainThread;
//         return (R) this;
//     }
//     //
//     // /**
//     //  * 设置请求的线程调度类型
//     //  *
//     //  * @param threadType
//     //  * @return
//     //  */
//     // public R threadType(@ThreadType String threadType) {
//     //     if (ThreadType.TO_MAIN.equals(threadType)) { // -> main -> io -> main
//     //         syncRequest(false).onMainThread(true);
//     //     } else if (ThreadType.TO_IO.equals(threadType)) { // -> main -> io -> io
//     //         syncRequest(false).onMainThread(false);
//     //     } else if (ThreadType.IN_THREAD.equals(threadType)) { // -> io -> io -> io
//     //         syncRequest(true).onMainThread(false);
//     //     }
//     //     return (R) this;
//     // }
//
//     //===========================================//
//     //             请求超时、重试设置                //
//     //===========================================//
//
//     /**
//      * 是否添加数字签名（默认false）
//      *
//      * @param sign
//      */
//     public R sign(boolean sign) {
//         mSign = sign;
//         return (R) this;
//     }
//
//     /**
//      * 是否添加时间戳（默认false）
//      *
//      * @param timeStamp
//      */
//     public R timeStamp(boolean timeStamp) {
//         mTimeStamp = timeStamp;
//         return (R) this;
//     }
//
//     /**
//      * 是否需要验证token（默认false)
//      *
//      * @param accessToken
//      */
//     public R accessToken(boolean accessToken) {
//         mAccessToken = accessToken;
//         return (R) this;
//     }
//
//     //===========================================//
//     //             请求超时、重试设置               //
//     //===========================================//
//
//     /**
//      * 设置读的超时时间
//      *
//      * @param readTimeOut
//      */
//     public R readTimeOut(long readTimeOut) {
//         mReadTimeOut = readTimeOut;
//         return (R) this;
//     }
//
//     /**
//      * 设置写的超时时间
//      *
//      * @param writeTimeOut
//      */
//     public R writeTimeOut(long writeTimeOut) {
//         mWriteTimeOut = writeTimeOut;
//         return (R) this;
//     }
//
//     /**
//      * 设置服务器连接的超时时间
//      *
//      * @param connectTimeout
//      */
//     public R connectTimeout(long connectTimeout) {
//         mConnectTimeout = connectTimeout;
//         return (R) this;
//     }
//
//     /**
//      * 设置超时时间（读、写、服务器连接的超时时间）
//      *
//      * @param timeOut
//      */
//     public R timeOut(long timeOut) {
//         mReadTimeOut = timeOut;
//         mWriteTimeOut = timeOut;
//         mConnectTimeout = timeOut;
//         return (R) this;
//     }
//
//     /**
//      * 设置超时重试的次数
//      *
//      * @param retryCount
//      */
//     public R retryCount(int retryCount) {
//         if (retryCount < 0) {
//             throw new IllegalArgumentException("mRetryCount must > 0");
//         }
//         mRetryCount = retryCount;
//         return (R) this;
//     }
//
//     /**
//      * 设置超时重试的延迟时间
//      *
//      * @param retryDelay
//      */
//     public R retryDelay(int retryDelay) {
//         if (retryDelay < 0) {
//             throw new IllegalArgumentException("mRetryDelay must > 0");
//         }
//         mRetryDelay = retryDelay;
//         return (R) this;
//     }
//
//     /**
//      * 设置超时重试叠加延时
//      *
//      * @param retryIncreaseDelay
//      */
//     public R retryIncreaseDelay(int retryIncreaseDelay) {
//         if (retryIncreaseDelay < 0) {
//             throw new IllegalArgumentException("mRetryIncreaseDelay must > 0");
//         }
//         mRetryIncreaseDelay = retryIncreaseDelay;
//         return (R) this;
//     }
//
//     //===========================================//
//     //            请求头，公共参数的设置             //
//     //===========================================//
//
//     /**
//      * 添加头信息
//      */
//     public R headers(HttpHeaders headers) {
//         mHeaders.set(headers);
//         return (R) this;
//     }
//
//     /**
//      * 添加头信息
//      */
//     public R headers(String key, String value) {
//         mHeaders.set(key, value);
//         return (R) this;
//     }
//
//     /**
//      * 移除头信息
//      */
//     public R removeHeader(String key) {
//         mHeaders.remove(key);
//         return (R) this;
//     }
//
//     /**
//      * 移除所有头信息
//      */
//     public R removeAllHeaders() {
//         mHeaders.clear();
//         return (R) this;
//     }
//
//     /**
//      * 设置参数
//      */
//     public R params(HttpParams params) {
//         mParams.put(params);
//         return (R) this;
//     }
//
//     /**
//      * 设置参数
//      */
//     public R params(Map<String, Object> params) {
//         mParams.put(params);
//         return (R) this;
//     }
//
//     /**
//      * 设置参数
//      */
//     public R params(String key, Object value) {
//         mParams.put(key, value);
//         return (R) this;
//     }
//
//     /**
//      * 去除参数
//      */
//     public R removeParam(String key) {
//         mParams.remove(key);
//         return (R) this;
//     }
//
//     /**
//      * 去除所有参数
//      */
//     public R removeAllParams() {
//         mParams.clear();
//         return (R) this;
//     }
//
//     public HttpParams getParams() {
//         return mParams;
//     }
//
//     //===========================================//
//     //               请求缓存设置                  //
//     //===========================================//
//     //
//     // /**
//     //  * 设置缓存（默认无缓存）
//     //  *
//     //  * @param cache
//     //  * @return
//     //  */
//     // public R okCache(Cache cache) {
//     //     mCache = cache;
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * 设置缓存的模式（默认无缓存）
//     //  *
//     //  * @param cacheMode
//     //  * @return
//     //  */
//     // public R cacheMode(CacheMode cacheMode) {
//     //     mCacheMode = cacheMode;
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * 设置缓存的key
//     //  *
//     //  * @param cacheKey
//     //  * @return
//     //  */
//     // public R cacheKey(String cacheKey) {
//     //     mCacheKey = cacheKey;
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * 设置缓存保存的时间（缓存过期时间，默认永久缓存）
//     //  *
//     //  * @param cacheTime
//     //  * @return
//     //  */
//     // public R cacheTime(long cacheTime) {
//     //     if (cacheTime <= -1) {
//     //         cacheTime = DEFAULT_CACHE_NEVER_EXPIRE;
//     //     }
//     //     mCacheTime = cacheTime;
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * 设置缓存的转换器
//     //  */
//     // public R cacheDiskConverter(IDiskConverter converter) {
//     //     mDiskConverter = Objects.requireNonNull(converter, "converter == null");
//     //     return (R) this;
//     // }
//
//     //===========================================//
//     //     OkHttpClient的拦截器、代理等设置          //
//     //===========================================//
//
//     /**
//      * 设置代理
//      */
//     public R okproxy(Proxy proxy) {
//         mProxy = proxy;
//         return (R) this;
//     }
//
//     /**
//      * 增加应用拦截器
//      *
//      * @param interceptor
//      */
//     public R addInterceptor(Interceptor interceptor) {
//         mInterceptors.add(Objects.requireNonNull(interceptor, "interceptor == null"));
//         return (R) this;
//     }
//
//     /**
//      * 增加网络拦截器
//      *
//      * @param interceptor
//      */
//     public R addNetworkInterceptor(Interceptor interceptor) {
//         mNetworkInterceptors.add(Objects.requireNonNull(interceptor, "interceptor == null"));
//         return (R) this;
//     }
//
//     //===========================================//
//     //         Retrofit的Factory设置              //
//     //===========================================//
//     //
//     // /**
//     //  * 设置Converter.Factory,默认GsonConverterFactory.create()
//     //  */
//     // public R addConverterFactory(Converter.Factory factory) {
//     //     mConverterFactories.add(factory);
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * 设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
//     //  */
//     // public R addCallAdapterFactory(CallAdapter.Factory factory) {
//     //     mAdapterFactories.add(factory);
//     //     return (R) this;
//     // }
//
//     //===========================================//
//     //                Https设置                   //
//     //===========================================//
//
//     /**
//      * https的全局访问规则
//      */
//     public R hostnameVerifier(HostnameVerifier hostnameVerifier) {
//         mHostnameVerifier = hostnameVerifier;
//         return (R) this;
//     }
//
//     // /**
//     //  * https的全局自签名证书
//     //  */
//     // public R certificates(InputStream... certificates) {
//     //     mSSLParams = HttpsObjects.getSslSocketFactory(null, null, certificates);
//     //     return (R) this;
//     // }
//     //
//     // /**
//     //  * https双向认证证书
//     //  */
//     // public R certificates(InputStream bksFile, String password, InputStream... certificates) {
//     //     mSSLParams = HttpsObjects.getSslSocketFactory(bksFile, password, certificates);
//     //     return (R) this;
//     // }
//
//     //===========================================//
//     //                Cookie设置                  //
//     //===========================================//
//
//     public R addCookie(String name, String value) {
//         Cookie.Builder builder = new Cookie.Builder();
//         Cookie cookie = builder.name(name).value(value).domain(mHttpUrl.host()).build();
//         mCookies.add(cookie);
//         return (R) this;
//     }
//
//     public R addCookie(Cookie cookie) {
//         mCookies.add(cookie);
//         return (R) this;
//     }
//
//     public R addCookies(List<Cookie> cookies) {
//         mCookies.addAll(cookies);
//         return (R) this;
//     }
//
//     //===========================================//
//     //               构建请求                     //
//     //===========================================//
//
//     /**
//      * 进行网络请求
//      *
//      * @return 网络请求的响应
//      */
//     protected ResponseBody generateRequestBody() {
//         return new ResponseBody() {
//             @Override
//             public MediaType contentType() {
//                 return null;
//             }
//
//             @Override
//             public long contentLength() {
//                 return 0;
//             }
//
//             @Override
//             public BufferedSource source() {
//                 return null;
//             }
//         };
//     }
//
//     /**
//      * 根据当前的请求参数，生成对应的OkClient
//      */
//     private OkHttpClient.Builder generateOkClient() {
//         // if (mReadTimeOut <= 0 && mWriteTimeOut <= 0 && mConnectTimeout <= 0 && mSSLParams == null
//         //         && mCookies.size() == 0 && mHostnameVerifier == null && mProxy == null && mHeaders.isEmpty()) {
//         //     OkHttpClient.Builder builder = httper.getOkHttpClientBuilder();
//         // for (Interceptor interceptor : builder.interceptors()) {
//         //     if (interceptor instanceof BaseDynamicInterceptor) {
//         //         ((BaseDynamicInterceptor) interceptor).sign(mSign).timeStamp(mTimeStamp).accessToken(mAccessToken);
//         //     }
//         // }
//         // return builder;
//         // } else {
//         final OkHttpClient.Builder newClientBuilder = mOkHttpClient.newBuilder();
//         if (mReadTimeOut > 0)
//             newClientBuilder.readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS);
//         if (mWriteTimeOut > 0)
//             newClientBuilder.writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS);
//         if (mConnectTimeout > 0)
//             newClientBuilder.connectTimeout(mConnectTimeout, TimeUnit.MILLISECONDS);
//         if (mHostnameVerifier != null) newClientBuilder.hostnameVerifier(mHostnameVerifier);
//         // if (mSSLParams != null)
//         //     newClientBuilder.sslSocketFactory(mSSLParams.sSLSocketFactory, mSSLParams.trustManager);
//         if (mProxy != null) newClientBuilder.proxy(mProxy);
//         // if (mCookies.size() > 0) Httper.getCookieJar().addCookies(mCookies);
//         // for (Interceptor interceptor : mInterceptors) {
//         //     if (interceptor instanceof BaseDynamicInterceptor) {
//         //         ((BaseDynamicInterceptor) interceptor).sign(mSign).timeStamp(mTimeStamp).accessToken(mAccessToken);
//         //     }
//         //     newClientBuilder.addInterceptor(interceptor);
//         // }
//         // for (Interceptor interceptor : newClientBuilder.interceptors()) {
//         //     if (interceptor instanceof BaseDynamicInterceptor) {
//         //         ((BaseDynamicInterceptor) interceptor).sign(mSign).timeStamp(mTimeStamp).accessToken(mAccessToken);
//         //     }
//         // }
//         if (mNetworkInterceptors.size() > 0) {
//             for (Interceptor interceptor : mNetworkInterceptors) {
//                 newClientBuilder.addNetworkInterceptor(interceptor);
//             }
//         }
//         //添加头
//         // newClientBuilder.addInterceptor(new HeadersInterceptor(mHeaders));
//         return newClientBuilder;
//     }
//
// //
// // /**
// //  * 根据当前的请求参数，生成对应的Retrofit
// //  */
// // private Retrofit.Builder generateRetrofit() {
// //     if (mConverterFactories.isEmpty() && mAdapterFactories.isEmpty()) {
// //         return Httper.getRetrofitBuilder().baseUrl(mBaseUrl);
// //     } else {
// //         final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
// //         if (!mConverterFactories.isEmpty()) {
// //             for (Converter.Factory converterFactory : mConverterFactories) {
// //                 retrofitBuilder.addConverterFactory(converterFactory);
// //             }
// //         } else {
// //             //获取全局的对象重新设置
// //             List<Converter.Factory> listConverterFactory = Httper.getRetrofitBuilder().converterFactories();
// //             for (Converter.Factory factory : listConverterFactory) {
// //                 retrofitBuilder.addConverterFactory(factory);
// //             }
// //         }
// //         if (!mAdapterFactories.isEmpty()) {
// //             for (CallAdapter.Factory adapterFactory : mAdapterFactories) {
// //                 retrofitBuilder.addCallAdapterFactory(adapterFactory);
// //             }
// //         } else {
// //             //获取全局的对象重新设置
// //             List<CallAdapter.Factory> listAdapterFactory = Httper.getRetrofitBuilder().callAdapterFactories();
// //             for (CallAdapter.Factory factory : listAdapterFactory) {
// //                 retrofitBuilder.addCallAdapterFactory(factory);
// //             }
// //         }
// //         return retrofitBuilder.baseUrl(mBaseUrl);
// //     }
// // }
// //
// // /**
// //  * 根据当前的请求参数，生成对应的RxCache和Cache
// //  */
// // private RxCache.Builder generateRxCache() {
// //     final RxCache.Builder rxCacheBuilder = Httper.getRxCacheBuilder();
// //     switch (mCacheMode) {
// //         case NO_CACHE://不使用缓存
// //             final NoCacheInterceptor NOCACHEINTERCEPTOR = new NoCacheInterceptor();
// //             mInterceptors.add(NOCACHEINTERCEPTOR);
// //             mNetworkInterceptors.add(NOCACHEINTERCEPTOR);
// //             break;
// //         case DEFAULT://使用OkHttp的缓存
// //             if (mCache == null) {
// //                 File cacheDirectory = Httper.getCacheDirectory();
// //                 if (cacheDirectory == null) {
// //                     cacheDirectory = new File(Httper.getContext().getCacheDir(), "okhttp-cache");
// //                 } else {
// //                     if (cacheDirectory.isDirectory() && !cacheDirectory.exists()) {
// //                         cacheDirectory.mkdirs();
// //                     }
// //                 }
// //                 mCache = new Cache(cacheDirectory, Math.max(5 * 1024 * 1024, Httper.getCacheMaxSize()));
// //             }
// //             String cacheControlValue = String.format("max-age=%d", Math.max(-1, mCacheTime));
// //             final CacheInterceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(Httper.getContext(), cacheControlValue);
// //             final CacheInterceptorOffline REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE = new CacheInterceptorOffline(Httper.getContext(), cacheControlValue);
// //             mNetworkInterceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
// //             mNetworkInterceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
// //             mInterceptors.add(REWRITE_CACHE_CONTROL_INTERCEPTOR_OFFLINE);
// //             break;
// //         case FIRST_REMOTE:
// //         case FIRST_CACHE:
// //         case ONLY_REMOTE:
// //         case ONLY_CACHE:
// //         case CACHE_REMOTE:
// //         case CACHE_REMOTE_DISTINCT:
// //             mInterceptors.add(new NoCacheInterceptor());
// //             if (mDiskConverter == null) {
// //                 final RxCache.Builder tempRxCacheBuilder = rxCacheBuilder;
// //                 tempRxCacheBuilder.cacheKey(Objects.requireNonNull(mCacheKey, "mCacheKey == null"))
// //                         .cacheTime(mCacheTime);
// //                 return tempRxCacheBuilder;
// //             } else {
// //                 final RxCache.Builder cacheBuilder = Httper.getRxCache().newBuilder();
// //                 cacheBuilder.diskConverter(mDiskConverter)
// //                         .cacheKey(Objects.requireNonNull(mCacheKey, "mCacheKey == null"))
// //                         .cacheTime(mCacheTime);
// //                 return cacheBuilder;
// //             }
// //     }
// //     return rxCacheBuilder;
// // }
// //
// // /**
// //  * 构建请求【构建RxCache、OkHttpClient、Retrofit、mApiManager】
// //  *
// //  * @return
// //  */
// // protected R build() {
// //     final RxCache.Builder rxCacheBuilder = generateRxCache();
// //     OkHttpClient.Builder okHttpClientBuilder = generateOkClient();
// //     if (mCacheMode == CacheMode.DEFAULT) {//okHttp缓存
// //         okHttpClientBuilder.cache(mCache);
// //     }
// //     final Retrofit.Builder retrofitBuilder = generateRetrofit();
// //     retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());//增加RxJavaCallAdapterFactory
// //     mOkHttpClient = okHttpClientBuilder.build();
// //     retrofitBuilder.client(mOkHttpClient);
// //     mRetrofit = retrofitBuilder.build();
// //     mRxCache = rxCacheBuilder.build();
// //     mApiManager = mRetrofit.create(ApiService.class);
// //     return (R) this;
// // }
// //
// // //===================请求执行===============================//
// //
// // public <T> Observable<T> execute(Class<T> clazz) {
// //     return execute(new CallClazzProxy<ApiResult<T>, T>(clazz) {
// //     });
// // }
// //
// // public <T> Observable<T> execute(Type type) {
// //     return execute(new CallClazzProxy<ApiResult<T>, T>(type) {
// //     });
// // }
// //
// // public <T> Disposable execute(CallBack<T> callBack) {
// //     return execute(new CallBackProxy<ApiResult<T>, T>(callBack) {
// //     });
// // }
// //
// // //==================================================//
// //
// // /**
// //  * 执行请求，获取请求响应结果【Observable<CacheResult<T>>】
// //  *
// //  * @param observable
// //  * @param proxy
// //  * @param <T>
// //  * @return
// //  */
// // protected <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
// //     return observable.map(new ApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
// //     }.getType(), mKeepJson))
// //             .compose(new HttpResultTransformer())
// //             .compose(new HttpSchedulersTransformer(mIsSyncRequest, mIsOnMainThread))
// //             .compose(mRxCache.transformer(mCacheMode, proxy.getCallBack().getType()))
// //             .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay));
// // }
// //
// // /**
// //  * 执行请求，并订阅请求响应结果(CallBack代理)
// //  *
// //  * @param proxy
// //  * @param <T>
// //  * @return
// //  */
// // public <T> Disposable execute(CallBackProxy<? extends ApiResult<T>, T> proxy) {
// //     Observable<CacheResult<T>> observable = build().toObservable(generateRequest(), proxy);
// //     if (CacheResult.class != proxy.getRawType()) {
// //         return observable.compose(new ObservableTransformer<CacheResult<T>, T>() {
// //             @Override
// //             public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
// //                 return upstream.map(new CacheResultFunc<T>());
// //             }
// //         }).subscribeWith(new CallBackSubscriber<T>(proxy.getCallBack()));
// //     } else {
// //         return observable.subscribeWith(new CallBackSubscriber<CacheResult<T>>(proxy.getCallBack()));
// //     }
// // }
// //
// // /**
// //  * 执行请求，获取请求响应结果【Observable<T>】
// //  *
// //  * @param proxy 使用了getType
// //  * @param <T>
// //  * @return
// //  */
// // public <T> Observable<T> execute(CallClazzProxy<? extends ApiResult<T>, T> proxy) {
// //     return build().generateRequest()
// //             .map(new ApiResultFunc(proxy.getType(), mKeepJson))
// //             .compose(new HttpResultTransformer())
// //             .compose(new HttpSchedulersTransformer(mIsSyncRequest, mIsOnMainThread))
// //             .compose(mRxCache.transformer(mCacheMode, proxy.getCallType()))
// //             .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay))
// //             .compose(new ObservableTransformer() {
// //                 @Override
// //                 public ObservableSource apply(@NonNull Observable upstream) {
// //                     return upstream.map(new CacheResultFunc<T>());
// //                 }
// //             });
// // }
//
// }
//
