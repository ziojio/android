package zhuj.http;

import java.util.Map;


/**
 * 全局请求头、参数
 */
public class RequestConfig {

    private final String TAG = getClass().getSimpleName();


    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 15000;     //默认的超时时间
    public static final int DEFAULT_RETRY_COUNT = 0;                  //默认重试次数
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 0;         //默认重试叠加时间
    public static final int DEFAULT_RETRY_DELAY = 500;                //默认重试延时
    public static final int DEFAULT_CACHE_NEVER_EXPIRE = -1;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSubUrl() {
        return subUrl;
    }

    // ====== url地址 =====
    private String baseUrl;                                          //全局BaseUrl
    private String subUrl = "";                                      //全局SubUrl,介于BaseUrl和请求url之间

    private HttpHeaders mCommonHeaders;                              //全局公共请求头
    private HttpParams mCommonParams;                                //全局公共请求参数


    protected Map<String, String> params;


    private RequestConfig() {

    }

    public RequestConfig getConfig() {
        return new RequestConfig();
    }


    /**
     * 添加全局公共请求参数
     */
    public RequestConfig addCommonParams(HttpParams commonParams) {
        if (mCommonParams == null) mCommonParams = new HttpParams();
        mCommonParams.put(commonParams);
        return this;
    }

    /**
     * 添加全局公共请求参数
     */
    public RequestConfig addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) mCommonHeaders = new HttpHeaders();
        mCommonHeaders.add(commonHeaders);
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


}
