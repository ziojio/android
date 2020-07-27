package com.zhuj.android;

// Declare any non-default types here with import statements
import android.content.Intent;

interface ICallBack {
    void onResult(int flag, out Intent intent);
}
