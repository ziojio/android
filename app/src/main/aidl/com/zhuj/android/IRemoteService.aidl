package com.zhuj.android;

// Declare any non-default types here with import statements

import com.zhuj.android.ICallBack;
import android.content.Intent;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    Intent syncFunction(String funcName, in Intent data);
    void asyncFunction(String funcName, in Intent data, ICallBack callback);
}
