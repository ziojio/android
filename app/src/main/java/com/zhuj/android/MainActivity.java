package com.zhuj.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhuj.android.android.Activitys;
import com.zhuj.android.android.Toasts;
import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.base.service.BaseService;
import com.zhuj.android.database.sqlitehelper.AppDatabase;
import com.zhuj.android.thread.WorkExecutor;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.ui.activity.ViewActivity;
import com.zhuj.android.ui.activity.WebViewActivity;
import com.zhuj.android.util.PackageUtils;

public class MainActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

    private TextView showText;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
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

        addClick(R.id.button_sql, R.id.button_test, R.id.button_webview, R.id.button_view, R.id.button_button, R.id.button_do);
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
        if (!PackageUtils.isAppInstalled("com.jbzh.jbpaintviewcore")) {
            Activitys.openActivity(this, "com.jbzh.jbpaintviewcore", "com.jbzh.editimage.MainActivity");
        } else {
            Toasts.show(MainActivity.this, "没有安装 com.jbzh.jbpaintviewcore");
        }

        // PackageUtils.isInstall(getPackageName() );
        // PackageUtils.isExistsActivity(getPackageName(), "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
