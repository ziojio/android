package com.zhuj.android;

// Declare any non-default types here with import statements

import com.zhuj.android.ICallBack;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String syncFunction(String funcName, String data);
    void asyncFunction(String funcName, String data, ICallBack callback);
}
