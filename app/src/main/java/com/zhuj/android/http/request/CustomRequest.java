// package com.zhuj.android.http.request;
//
//
// import java.util.Observable;
//
// import io.reactivex.ObservableSource;
// import io.reactivex.disposables.Disposable;
// import okhttp3.ResponseBody;
//
// /**
//  *  <p>描述：自定义请求，例如你有自己的ApiService</p>
//  *
//  * @author xuexiang
//  * @since 2018/6/25 下午8:16
//  */
// @SuppressWarnings(value={"unchecked"})
// public class CustomRequest extends BaseRequest<CustomRequest> {
//
//     public CustomRequest() {
//         super("");
//     }
//
//     @Override
//     public CustomRequest build() {
//         return super.build();
//     }
//
//     /**
//      * 创建api服务  可以支持自定义的api，默认使用BaseApiService,上层不用关心
//      *
//      * @param service 自定义的ApiService class
//      */
//     public <T> T create(final Class<T> service) {
//         checkValidate();
//         return mRetrofit.create(service);
//     }
//
//     private void checkValidate() {
//         Utils.checkNotNull(mRetrofit, "请先在调用build()才能使用");
//     }
//
//
//     //=================apiCall====================//
//
//     /**
//      * 针对retrofit定义的接口，返回的是Observable<ApiResult<T>>的情况<br>
//      *
//      * 对ApiResult进行拆包，直接获取数据
//      *
//      * @param observable retrofit定义接口返回的类型
//      */
//     public <T> Observable<T> apiCall(Observable<? extends ApiResult<T>> observable) {
//         checkValidate();
//         return observable
//                 .compose(new HttpResultTransformer())
//                 .compose(new HttpSchedulersTransformer(mIsSyncRequest, mIsOnMainThread))
//                 .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay));
//     }
//
//     /**
//      * 针对retrofit定义的接口，返回的是Observable<ApiResult<T>>的情况<br>
//      *
//      * 对ApiResult进行拆包，直接获取数据
//      *
//      * @param observable retrofit定义接口返回的类型
//      */
//     public <T> Disposable apiCall(Observable observable, CallBack<T> callBack) {
//         return call(observable, new CallBackProxy<ApiResult<T>, T>(callBack){});
//     }
//
//     //=================call====================//
//     /**
//      * 针对retrofit定义的接口，返回的是Observable<T>的情况<br>
//      *
//      * 不对ApiResult进行拆包，返回服务端响应的ApiResult
//      *
//      * @param observable retrofit定义接口返回的类型
//      */
//     public <T> Observable<T> call(Observable<T> observable) {
//         checkValidate();
//         return observable
//                 .compose(new HandleErrTransformer())
//                 .compose(new HttpSchedulersTransformer(mIsSyncRequest, mIsOnMainThread))
//                 .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay));
//     }
//
//     /**
//      * 针对retrofit定义的接口，返回的是Observable<T>的情况<br>
//      *
//      * 不对ApiResult进行拆包，返回服务端响应的ApiResult
//      *
//      * @param observable retrofit定义接口返回的类型
//      */
//     public <T> void call(Observable<T> observable, CallBack<T> callBack) {
//         call(observable, new CallBackSubscriber(callBack));
//     }
//
//     public <R> void call(Observable observable, Observer<R> subscriber) {
//         call(observable).subscribe(subscriber);
//     }
//
//     /**
//      * @param observable
//      * @param proxy
//      * @param <T>
//      * @return
//      */
//     public <T> Disposable call(Observable<T> observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
//         Observable<CacheResult<T>> cacheObservable = build().toObservable(observable, proxy);
//         if (CacheResult.class != proxy.getRawType()) {
//             return cacheObservable.compose(new ObservableTransformer<CacheResult<T>, T>() {
//                 @Override
//                 public ObservableSource<T> apply(@NonNull Observable<CacheResult<T>> upstream) {
//                     return upstream.map(new CacheResultFunc<T>());
//                 }
//             }).subscribeWith(new CallBackSubscriber<T>(proxy.getCallBack()));
//         } else {
//             return cacheObservable.subscribeWith(new CallBackSubscriber<CacheResult<T>>(proxy.getCallBack()));
//         }
//     }
//
//     @Override
//     protected <T> Observable<CacheResult<T>> toObservable(Observable observable, CallBackProxy<? extends ApiResult<T>, T> proxy) {
//         checkValidate();
//         return observable
//                 .compose(new HttpResultTransformer())
//                 .compose(new HttpSchedulersTransformer(mIsSyncRequest, mIsOnMainThread))
//                 .compose(mRxCache.transformer(mCacheMode, proxy.getCallBack().getType()))
//                 .retryWhen(new RetryExceptionFunc(mRetryCount, mRetryDelay, mRetryIncreaseDelay));
//     }
//
//     @Override
//     protected Observable<ResponseBody> generateRequest() {
//         return null;
//     }
// }
