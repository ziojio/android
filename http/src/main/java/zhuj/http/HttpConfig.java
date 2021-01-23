package zhuj.http;


import zhuj.http.ssl.SSLUtils;
import zhuj.http.util.Network;

import java.net.CookieStore;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;

public final class HttpConfig {

    public static Builder newBuilder() {
        return new Builder();
    }

//    private final Executor mWorkExecutor;
//    private final Executor mMainExecutor;

    private final Charset mCharset;
    private final HttpHeaders mHttpHeaders;
    private final Proxy mProxy;
    private final SSLSocketFactory mSSLSocketFactory;
    private final HostnameVerifier mHostnameVerifier;
    private final int mConnectTimeout;
    private final int mReadTimeout;
    private final HttpParams mParams;


    private final Network mNetwork;
//    private final ConnectFactory mConnectFactory;
//    private final CookieStore mCookieStore;
    private final List<Interceptor> mInterceptors;

//    private final Converter mConverter;

    private HttpConfig(Builder builder) {
//        this.mWorkExecutor = builder.mWorkExecutor == null ? new WorkExecutor() : builder.mWorkExecutor;
//        this.mMainExecutor = builder.mMainExecutor == null ? new MainExecutor() : builder.mMainExecutor;

        this.mCharset = builder.mCharset == null ? Charset.defaultCharset() : builder.mCharset;
        this.mHttpHeaders = builder.mHttpHeaders;
        this.mProxy = builder.mProxy;
        this.mSSLSocketFactory = builder.mSSLSocketFactory == null ? SSLUtils.SSL_SOCKET_FACTORY : builder.mSSLSocketFactory;
        this.mHostnameVerifier = builder.mHostnameVerifier == null ? SSLUtils.HOSTNAME_VERIFIER : builder.mHostnameVerifier;
        this.mConnectTimeout = builder.mConnectTimeout <= 0 ? 10000 : builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout <= 0 ? 10000 : builder.mReadTimeout;
        this.mParams = builder.mParams;

        this.mNetwork = builder.mNetwork == null ? Network.DEFAULT : builder.mNetwork;
//        this.mConnectFactory = builder.mConnectFactory == null ? URLConnectionFactory.newBuilder().build() : builder.mConnectFactory;
//        this.mCookieStore = builder.mCookieStore == null ? CookieStore.DEFAULT : builder.mCookieStore;
        this.mInterceptors = Collections.unmodifiableList(builder.mInterceptors);

//        this.mConverter = builder.mConverter == null ? Converter.DEFAULT : builder.mConverter;
    }

//    public Executor getWorkExecutor() {
//        return mWorkExecutor;
//    }
//
//    public Executor getMainExecutor() {
//        return mMainExecutor;
//    }

    public Charset getCharset() {
        return mCharset;
    }

    public HttpHeaders getHttpHeaders() {
        return mHttpHeaders;
    }

    public Proxy getProxy() {
        return mProxy;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return mSSLSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public HttpParams getParams() {
        return mParams;
    }


    public Network getNetwork() {
        return mNetwork;
    }


//    public CookieStore getCookieStore() {
//        return mCookieStore;
//    }

    public List<Interceptor> getInterceptor() {
        return mInterceptors;
    }


    public final static class Builder {

        private Executor mWorkExecutor;
        private Executor mMainExecutor;

        private Charset mCharset;
        private HttpHeaders mHttpHeaders;
        private Proxy mProxy;
        private SSLSocketFactory mSSLSocketFactory;
        private HostnameVerifier mHostnameVerifier;
        private int mConnectTimeout;
        private int mReadTimeout;
        private HttpParams mParams;


        private Network mNetwork;
        private CookieStore mCookieStore;
        private List<Interceptor> mInterceptors;


        private Builder() {
            this.mHttpHeaders = new HttpHeaders();
            this.mParams = new HttpParams() ;
            this.mInterceptors = new ArrayList<>();

            mHttpHeaders.set(HttpHeaders.KEY_ACCEPT, HttpHeaders.VALUE_ACCEPT_ALL);
            mHttpHeaders.set(HttpHeaders.KEY_ACCEPT_ENCODING, HttpHeaders.VALUE_ACCEPT_ENCODING);
            mHttpHeaders.set(HttpHeaders.KEY_CONTENT_TYPE, HttpHeaders.VALUE_APPLICATION_URLENCODED);
            mHttpHeaders.set(HttpHeaders.KEY_CONNECTION, HttpHeaders.VALUE_KEEP_ALIVE);
            // mHttpHeaders.set(HttpHeaders.KEY_USER_AGENT, HttpHeaders.getUserAgent(Httper.getInstance().getContext()));
            mHttpHeaders.set(HttpHeaders.KEY_ACCEPT_LANGUAGE, HttpHeaders.getAcceptLanguage());
        }

        /**
         * Set global work thread executor.
         */
        public Builder workThreadExecutor(Executor executor) {
            this.mWorkExecutor = executor;
            return this;
        }

        /**
         * Set global main thread executor.
         */
        public Builder mainThreadExecutor(Executor executor) {
            this.mMainExecutor = executor;
            return this;
        }

        /**
         * Global charset.
         */
        public Builder charset(Charset charset) {
            this.mCharset = charset;
            return this;
        }

        /**
         * Add the global header.
         */
        public Builder addHeader(String key, String value) {
            mHttpHeaders.add(key, value);
            return this;
        }

        /**
         * Set the global header.
         */
        public Builder setHeader(String key, String value) {
            mHttpHeaders.set(key, value);
            return this;
        }

        /**
         * Global proxy.
         */
        public Builder proxy(Proxy proxy) {
            this.mProxy = proxy;
            return this;
        }

        /**
         * Global ssl socket factory.
         */
        public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.mSSLSocketFactory = sslSocketFactory;
            return this;
        }

        /**
         * Global hostname verifier.
         */
        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        /**
         * Global connection timeout.
         */
        public Builder connectionTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mConnectTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Global readResponse timeout.
         */
        public Builder readTimeout(int timeout, TimeUnit timeUnit) {
            long time = timeUnit.toMillis(timeout);
            this.mReadTimeout = (int) Math.min(time, Integer.MAX_VALUE);
            return this;
        }

        /**
         * Add the global param.
         */
        public Builder addParam(String key, String value) {
            mParams.put(key, value);
            return this;
        }

        /**
         * Global cache store.
         */
//        public Builder cacheStore(CacheStore cacheStore) {
//            this.mCacheStore = cacheStore;
//            return this;
//        }

        /**
         * Global network.
         */
        public Builder network(Network network) {
            this.mNetwork = network;
            return this;
        }

        /**
         * Global cookie store.
         */
//        public Builder connectFactory(ConnectFactory factory) {
//            this.mConnectFactory = factory;
//            return this;
//        }

        /**
         * Global cookie store.
         */
        public Builder cookieStore(CookieStore cookieStore) {
            this.mCookieStore = cookieStore;
            return this;
        }

        /**
         * Add the global interceptor.
         */
        public Builder addInterceptor(Interceptor interceptor) {
            this.mInterceptors.add(interceptor);
            return this;
        }

        /**
         * Add the global interceptor.
         */
        public Builder addInterceptors(List<Interceptor> interceptors) {
            this.mInterceptors.addAll(interceptors);
            return this;
        }

        /**
         * The converter.
         */
//        public Builder converter(Converter converter) {
//            this.mConverter = converter;
//            return this;
//        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }

}