package com.zhuj.android;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.data.database.sqlitehelper.AppDatabase;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.thread.WorkExecutor;
import com.zhuj.android.ui.activity.WebViewActivity;
import com.zhuj.android.ui.activity.ViewActivity;


public class MainActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

    private TextView showText;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int toolbarId() {
        return R.id.toolbar;
    }

    private void initActionBar() {
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

        addClick(R.id.button_sql, R.id.button_test, R.id.button_webview, R.id.button_view, R.id.button_do);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sql:
                Logger.d("start ");
                startActivity(new Intent(mActivity, MainActivity.class));
                break;
            case R.id.button_test:
                Logger.d("start test");
                startActivity(new Intent(mActivity, TestActivity.class));
                break;
            case R.id.button_webview:
                startActivity(new Intent(mActivity, WebViewActivity.class));
                break;
            case R.id.button_view:
                startActivity(new Intent(mActivity, ViewActivity.class));
                break;
            case R.id.button_do:
                doSomething();
                break;
        }
    }

    private void doSomething() {

    }

}
