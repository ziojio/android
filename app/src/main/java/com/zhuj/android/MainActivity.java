package com.zhuj.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.base.service.BaseService;
import com.zhuj.android.database.sqlitehelper.AppDatabase;
import com.zhuj.comutils.thread.WorkExecutor;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.ui.activity.ViewActivity;
import com.zhuj.android.ui.activity.WebViewActivity;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addClickListener(this::onClick, R.id.button_sql, R.id.button_test, R.id.button_webview,
                R.id.button_view, R.id.button_button, R.id.button_do);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_sql) {
            startActivity(new Intent(mActivity, MainActivity.class));
        } else if (id == R.id.button_test) {
            startActivity(new Intent(mActivity, TestActivity.class));
        } else if (id == R.id.button_webview) {
            startActivity(new Intent(mActivity, WebViewActivity.class));
        } else if (id == R.id.button_view) {
            startActivity(new Intent(mActivity, ViewActivity.class));
        } else if (id == R.id.button_button) {// bindService();
            Intent intent = new Intent(this, BaseService.class);
            startService(intent);
            // startActivity(new Intent(mActivity, TestFragmentActivity.class));
        } else if (id == R.id.button_do) {
            doSomething();
        }
    }

    void doSomething() {

        findViewById(R.id.btn).setSelected(true);

        // if (!PackageUtils.isAppInstalled("com.jbzh.jbpaintviewcore")) {
        //     Activitys.openActivity(this, "com.jbzh.jbpaintviewcore", "com.jbzh.editimage.MainActivity");
        // } else {
        //     Toasts.show(MainActivity.this, "没有安装 com.jbzh.jbpaintviewcore");
        // }

        // PackageUtils.isInstall(getPackageName() );
        // PackageUtils.isExistsActivity(getPackageName(), "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
