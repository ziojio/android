package zhuj.http;

import android.content.Context;

import zhuj.http.request.RequestFilter;
import zhuj.http.response.ApiCallback;
import zhuj.http.response.ResponseParser;
import com.zhuj.code.lang.Exceptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class Httper {
    private final String TAG = getClass().getSimpleName();

    private Context appContext;

    // default config
    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 15000;     // 默认的超时时间
    public static final int DEFAULT_RETRY_COUNT = 0;                  // 默认重试次数
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 0;         // 默认重试叠加时间
    public static final int DEFAULT_RETRY_DELAY = 500;                // 默认重试延时
    public static final int DEFAULT_CACHE_NEVER_EXPIRE = -1;

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public String getSubUrl() {
        return mSubUrl;
    }

    //======url地址=====//
    private String mBaseUrl;                                          // 全局BaseUrl
    private String mSubUrl = "";                                      // 全局SubUrl,介于BaseUrl和请求url之间

    //======全局请求头、参数=====//
    private HttpHeaders mCommonHeaders;                               // 全局公共请求头
    private HttpParams mCommonParams;                                 // 全局公共请求参数

    private Request.Builder requestBuilder = new Request.Builder();

    private OkHttpClient mOkHttpClient;                               // okHttp请求的客户端

    private ApiService apiService = new Retrofit.Builder().client(mOkHttpClient).build().create(ApiService.class);

    protected RequestFilter filter; // 默认请求处理
    protected ResponseParser parser; // 默认结果解析


    public void init(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    public Context getContext() {
        if (appContext == null) {
            throw new IllegalStateException("context is null, please init in use before!");
        }
        return appContext;
    }

    /**
     * 初始化
     */
    private Httper() {
        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
        //        mOkHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        mOkHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        mOkHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //        mRetrofitBuilder = new Retrofit.Builder();
        //        mRxCacheBuilder = new RxCache.Builder().init(sContext)
        //                .diskConverter(new SerializableDiskConverter());      //目前只支持Serializable和Gson缓存其它可以自己扩展
        mOkHttpClient = mOkHttpClientBuilder.build();
    }

    //==================获取Request请求=====================//
    //
    // /**
    //  * @return get请求
    //  */
    // public GetRequest get(String url) {
    //     return new GetRequest(this).url(url);
    // }
    //
    // /**
    //  * @return post请求
    //  */
    // public PostRequest post(String url) {
    //     return new PostRequest(this).url(url);
    // }

    // /**
    //  * @return delete请求
    //  */
    // public   DeleteRequest delete(String url) {
    //     return new DeleteRequest(url);
    // }
    //
    // /**
    //  * @return put请求
    //  */
    // public   PutRequest put(String url) {
    //     return new PutRequest(url);
    // }
    //
    // /**
    //  * @return 自定义请求
    //  */
    // public   CustomRequest custom() {
    //     return new CustomRequest()
    //             .addConverterFactory(GsonConverterFactory.create(new Gson()))
    //             .build();
    // }
    //
    // /**
    //  * @return 自定义请求
    //  */
    // public   <T> T custom(final Class<T> service) {
    //     return new CustomRequest()
    //             .addConverterFactory(GsonConverterFactory.create(new Gson()))
    //             .build()
    //             .create(service);
    // }

    // /**
    //  * @return 下载请求
    //  */
    // public   DownloadRequest downLoad(String url) {
    //     return new DownloadRequest(url);
    // }

    public static HttpsURLConnection getConfig() {
        return null;
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
     * 添加全局公共请求参数, 没有清空，覆盖相同的key值
     */
    public Httper addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.set(commonHeaders);
        return this;
    }

    /**
     * 获取全局公共请求参数
     */
    public HttpParams getCommonParams() {
        return mCommonParams;
    }

    /**
     * 获取全局公共请求头
     */
    public HttpHeaders getCommonHeaders() {
        return mCommonHeaders;
    }

    /**
     * 设置结果解析器，仅当不是JSON返回格式时才需要设置
     *
     * @param parser 结果解析器
     * @return Httper
     */
    public Httper parser(ResponseParser parser) {
        if (parser == null) {
            Exceptions.throwIllegalArgument("parser is null");
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
            Exceptions.throwIllegalArgument("filter is null");
        }
        this.filter = filter;
        return this;
    }

    public void asyncCall(Request request, ApiCallback callback) {
        new OkHttpClient().newCall(request).enqueue(callback);
    }

    public void syncCall(Request request, ApiCallback callback) {
        Call call = new OkHttpClient().newCall(request);
        try (Response response = call.execute()) {
            callback.onResponse(call, response);
        } catch (IOException e) {
            callback.onFailure(call, e);
        }
    }

    public int getRetryCount() {
        return DEFAULT_RETRY_COUNT;
    }

    public int getRetryDelay() {
        return DEFAULT_RETRY_DELAY;
    }

    public int getRetryIncreaseDelay() {
        return DEFAULT_RETRY_INCREASE_DELAY;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
