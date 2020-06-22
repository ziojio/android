package com.zhuj.android.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhuj.android.R;
import com.zhuj.android.base.activity.BaseActivity;

public class TestActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int a = 15;
        int a1 = -15;
        byte b = 15;
        byte b1 = -15;
        Log.d(TAG, "onCreate: b1=" + (a1 >> 24));
        Log.d(TAG, "onCreate: ret=" + (b | b1));
        findViewById(0);
    }

    @Override
    public void onClick(View v) {

    }
}
