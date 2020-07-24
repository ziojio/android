package com.zhuj.android;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zhuj.android.base.activity.BaseActivity;
import com.zhuj.android.base.service.BaseService;
import com.zhuj.android.data.database.sqlitehelper.AppDatabase;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.thread.WorkExecutor;
import com.zhuj.android.ui.activity.TestFragmentActivity;
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
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();

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
                    ServiceConnection serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Service myservice = ((BaseService.ServiceBinder) service).getService();
                    }

                    /**
                     * Android系统在与Service的连接意外丢失时调用这个
                     * 当service崩溃了或被强杀了
                     * 当客户端解除绑定时，这个方法不会被调用
                     */
                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                Intent intent = new Intent(this,  BaseService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                // startActivity(new Intent(mActivity, TestFragmentActivity.class));
                break;
            case R.id.button_do:
                doSomething();
                break;
        }
    }

    private void doSomething() {

    }

}
