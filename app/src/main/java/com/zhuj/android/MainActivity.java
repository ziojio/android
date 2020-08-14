package com.zhuj.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.base.service.BaseService;
import com.zhuj.android.data.database.sqlitehelper.AppDatabase;
import com.zhuj.android.logger.Logger;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.thread.WorkExecutor;
import com.zhuj.android.ui.activity.WebViewActivity;
import com.zhuj.android.ui.activity.ViewActivity;

import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

    private TextView showText;

    @Override
    protected int layoutId() {
        return R.layout.xx;
    }


    @Override
    protected void initView() {
        Button button = findViewById( R.id.button_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setPressed(!button.isPressed());
            }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    private void initActionBar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // addClick(R.id.button_sql, R.id.button_test, R.id.button_webview, R.id.button_view, R.id.button_button, R.id.button_do);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sql:
                startActivity(new Intent(mActivity, MainActivity.class));
                break;
            case R.id.button_test:
                startActivity(new Intent(mActivity, TestActivity.class));
                break;
            case R.id.button_webview:
                startActivity(new Intent(mActivity, WebViewActivity.class));
                break;
            case R.id.button_view:
                startActivity(new Intent(mActivity, ViewActivity.class));
                break;
            case R.id.button_button:
                // bindService();
                Intent intent = new Intent(this, BaseService.class);
                startService(intent);
                // startActivity(new Intent(mActivity, TestFragmentActivity.class));
                break;
            case R.id.button_do:
                doSomething();
                break;
        }
    }

    void doSomething() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
