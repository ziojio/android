package com.zhuj.android.http.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by Zhenjie Yan on 2017/6/13.
 */
public class SSLUtils {

    public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    public static final SSLSocketFactory SSL_SOCKET_FACTORY = new TLSSocketFactory();
}