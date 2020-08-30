// package com.zhuj.android.http.request.method;
//
// import android.database.Observable;
//
// import com.zhuj.android.http.request.BaseRequest;
//
// import lombok.NonNull;
// import okhttp3.ResponseBody;
//
// /**
//  * <p>描述：下载请求</p>
//  *
//  * @author xuexiang
//  * @since 2018/6/25 上午12:53
//  */
// public class DownloadRequest extends BaseRequest<DownloadRequest> {
//
//     public DownloadRequest(String url) {
//         super(url);
//     }
//
//     /**
//      * 下载文件的路径
//      */
//     private String mSavePath;
//     /**
//      * 下载文件的名称
//      */
//     private String mSaveName;
//
//     /**
//      * 是否使用baseUrl
//      */
//     private boolean mIsUseBaseUrl;
//
//     /**
//      * 设置下载文件路径<br>
//      * SD卡不存在: /data/data/com.xxx.xxx/files;<br>
//      * 存在: /storage/emulated/0/Android/data/com.xxx.xxx/files;
//      */
//     public DownloadRequest savePath(String savePath) {
//         mSavePath = savePath;
//         return this;
//     }
//
//     /**
//      * 设置下载文件名称<br>
//      * 默认名字是时间戳生成的<br>
//      */
//     public DownloadRequest saveName(String saveName) {
//         mSaveName = saveName;
//         return this;
//     }
//
//     public DownloadRequest isUseBaseUrl(boolean isUseBaseUrl) {
//         mIsUseBaseUrl = isUseBaseUrl;
//         return this;
//     }
//
//     @Override
//     public <T> Disposable execute(CallBack<T> callBack) {
//         return (Disposable) build().generateRequest().compose(new ObservableTransformer<ResponseBody, ResponseBody>() {
//             @Override
//             public ObservableSource<ResponseBody> apply(@NonNull Observable<ResponseBody> upstream) {
//                 if (mIsSyncRequest) {
//                     return upstream;//.observeOn(AndroidSchedulers.mainThread());
//                 } else {
//                     return upstream.subscribeOn(Schedulers.io())
//                             .unsubscribeOn(Schedulers.io())
//                             .observeOn(Schedulers.computation());
//                 }
//             }
//         }).compose(new HandleErrTransformer())
//                 .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay))
//                 .subscribeWith(new DownloadSubscriber(mSavePath, mSaveName, callBack));
//     }
//
//     @Override
//     protected Observable<ResponseBody> generateRequest() {
//         if (mIsUseBaseUrl) {
//             return mApiManager.downloadFile(getBaseUrl() + getUrl());
//         } else {
//             return mApiManager.downloadFile(mUrl);
//         }
//     }
// }
