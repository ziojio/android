package com.zhuj.android;

import com.zhuj.android.ICallBack;
import android.content.Intent;

interface IRemoteService {

    Intent syncFunction(String funcName, in Intent data);
    void asyncFunction(String funcName, in Intent data, ICallBack callback);
}
