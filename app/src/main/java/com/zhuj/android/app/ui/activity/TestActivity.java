package com.zhuj.android.app.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhuj.android.app.R;
import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.http.Httper2;
import com.zhuj.android.http.callback.Callback;

public class TestActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Httper2.ME.get("http://www.baidu.com").callback(new Callback<String>(){

            @Override
            public void onSuccess(@NonNull String s) {
                runOnUiThread(()->{
                    TextView textView = findViewById(R.id.text);
                    textView.setText(s);
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        } );

    }

}
