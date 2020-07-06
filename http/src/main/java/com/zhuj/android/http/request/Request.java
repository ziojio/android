 package com.zhuj.android.http.request;

 import com.zhuj.android.http.HttpHeaders;
 import com.zhuj.android.http.HttpUrl;
 import com.zhuj.android.http.Httper;
 import com.zhuj.android.http.Params;
 import com.zhuj.android.http.RequestMethod;
 import com.zhuj.android.http.request.body.RequestBody;

 import java.net.Proxy;
 import java.util.List;
 import java.util.concurrent.TimeUnit;

 import javax.net.ssl.HostnameVerifier;
 import javax.net.ssl.SSLSocketFactory;



 public abstract class Request {

     private final RequestMethod mMethod;
     private final HttpHeaders mHttpHeaders;

//     private final Proxy mProxy;
     private final SSLSocketFactory mSSLSocketFactory;
     private final HostnameVerifier mHostnameVerifier;
     private final int mConnectTimeout;
     private final int mReadTimeout;
     private final Object mTag;

     protected <T extends Builder<T>> Request(Builder<T> builder) {
         this.mMethod = builder.mMethod;
         this.mHttpHeaders = builder.mHttpHeaders;

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
      * Get proxy server.
      */
//     public Proxy proxy() {
//         return mProxy;
//     }

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

     public static abstract class Builder<T extends Builder<T>> {

         private final RequestMethod mMethod;
         private final HttpHeaders mHttpHeaders = new HttpHeaders();
//         private Proxy mProxy = Httper.getConfig().getProxy();
         private SSLSocketFactory mSSLSocketFactory = Httper.getConfig().getSSLSocketFactory();
         private HostnameVerifier mHostnameVerifier = Httper.getConfig().getHostnameVerifier();
         private int mConnectTimeout = Httper.getConfig().getConnectTimeout();
         private int mReadTimeout = Httper.getConfig().getReadTimeout();
         private Object mTag;
 //
         protected Builder(RequestMethod method) {
             this.mMethod = method;
 //            this.mHttpHeaders.add(Httper.getConfig().getHttpHeaders());
         }

         /**
          * Overlay path.
          */
         public abstract T path(int value);

         /**
          * Overlay path.
          */
         public abstract T path(long value);

         /**
          * Overlay path.
          */
         public abstract T path(boolean value);

         /**
          * Overlay path.
          */
         public abstract T path(char value);

         /**
          * Overlay path.
          */
         public abstract T path(double value);

         /**
          * Overlay path.
          */
         public abstract T path(float value);

         /**
          * Overlay path.
          */
         public abstract T path(String value);

         /**
          * Add a new header.
          */
         public T addHeader(String key, String value) {
             mHttpHeaders.add(key, value);
             return (T) this;
         }

         /**
          * If the target key exists, replace it, if not, add.
          */
         public T setHeader(String key, String value) {
             mHttpHeaders.set(key, value);
             return (T) this;
         }

         /**
          * Set HttpHeaders.
          */
         public T setHttpHeaders(HttpHeaders HttpHeaders) {
             mHttpHeaders.set(HttpHeaders);
             return (T) this;
         }

         /**
          * Remove the key from the information.
          */
         public T removeHeader(String key) {
             mHttpHeaders.remove(key);
             return (T) this;
         }

         /**
          * Remove all header.
          */
         public T clearHttpHeaders() {
             mHttpHeaders.clear();
             return (T) this;
         }

         /**
          * Add parameter.
          */
         public abstract T param(String key, int value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, long value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, boolean value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, char value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, double value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, float value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, short value);

         /**
          * Add parameter.
          */
         public abstract T param(String key, String value);

         /**
          * Add parameters.
          */
         public abstract T param(String key, List<String> value);

         /**
          * Remove parameters.
          */
         public abstract T removeParam(String key);

         /**
          * Clear parameters.
          */
         public abstract T clearParams();

         /**
          * Proxy information for this request.
          */
 //        public T proxy(Proxy proxy) {
 //            this.mProxy = proxy;
 //            return (T) this;
 //        }

         /**
          * SSLSocketFactory for this request.
          */
         public T sslSocketFactory(SSLSocketFactory sslSocketFactory) {
             this.mSSLSocketFactory = sslSocketFactory;
             return (T) this;
         }

         /**
          * HostnameVerifier for this request.
          */
         public T hostnameVerifier(HostnameVerifier hostnameVerifier) {
             this.mHostnameVerifier = hostnameVerifier;
             return (T) this;
         }

         /**
          * Connect timeout for this request.
          */
         public T connectTimeout(int timeout, TimeUnit timeUnit) {
             long time = timeUnit.toMillis(timeout);
             this.mConnectTimeout = (int) Math.min(time, Integer.MAX_VALUE);
             return (T) this;
         }

         /**
          * Read timeout for this request.
          */
         public T readTimeout(int timeout, TimeUnit timeUnit) {
             long time = timeUnit.toMillis(timeout);
             this.mReadTimeout = (int) Math.min(time, Integer.MAX_VALUE);
             return (T) this;
         }

         /**
          * Tag.
          */
         public T tag(Object tag) {
             this.mTag = tag;
             return (T) this;
         }
     }
 }