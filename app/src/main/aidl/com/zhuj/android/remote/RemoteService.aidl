package com.zhuj.android.remote;

import com.zhuj.android.remote.CallBack;
import android.content.Intent;

interface RemoteService {

    Intent syncFunction(String funcName, in Intent data);
    void asyncFunction(String funcName, in Intent data, CallBack callback);
}
