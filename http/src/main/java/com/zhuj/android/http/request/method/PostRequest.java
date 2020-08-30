// package com.zhuj.android.http.request.method;
//
// import android.text.TextUtils;
//
// import com.zhuj.android.http.Httper;
// import com.zhuj.android.http.request.BaseBodyRequest;
//
// public class PostRequest extends BaseBodyRequest<PostRequest> {
//
//    public PostRequest(String url) {
//        super(url);
//    }
//
//    /**
//     * 使用xHttpRequest来构建post请求
//     * @param xHttpRequest 统一封装的请求实体对象
//     */
//    public PostRequest(XHttpRequest xHttpRequest) {
//        super(xHttpRequest.getUrl());
//        initRequest(xHttpRequest);
//    }
//
//     public PostRequest(Httper httper) {
//         super(httper);
//     }
//
//     /**
//     * 初始化请求
//     *
//     * @param xHttpRequest
//     */
//    private void initRequest(XHttpRequest xHttpRequest) {
//        String baseUrl = xHttpRequest.getBaseUrl();
//        String url = xHttpRequest.getUrl();
//        long timeout = xHttpRequest.getTimeout();
//        boolean accessToken = xHttpRequest.isAccessToken();
//        CacheMode cacheMode = xHttpRequest.getCacheMode();
//
//        if (!TextUtils.isEmpty(baseUrl)) {
//            baseUrl(baseUrl);
//        }
//        if (!CacheMode.NO_CACHE.equals(cacheMode)) {
//            cacheMode(cacheMode).cacheKey(url);
//        }
//        if (timeout <= 0) {   //如果超时时间小于等于0，使用默认的超时时间
//            timeout = XHttp.DEFAULT_TIMEOUT_MILLISECONDS;
//        }
//        accessToken(accessToken).timeOut(timeout).upJson(xHttpRequest.toString());
//    }
//
// }
