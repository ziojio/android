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
                // bindService();
                Logger.d("ssss");
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
        com.zhuj.android.logger.timber.Logger.plant(new com.zhuj.android.logger.timber.Logger.DebugTree());
        new Thread(new Runnable() {
            @Override
            public void run() {
                com.zhuj.android.logger.timber.Logger.tag("A TAG");
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    com.zhuj.android.logger.timber.Logger.d("------------ thread A -----------");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // com.zhuj.android.logger.timber.Logger.tag("B TAG");
                int x = 3;
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(x <= 0){
                        com.zhuj.android.logger.timber.Logger.tag("BBB");
                    }
                    x --;
                    com.zhuj.android.logger.timber.Logger.d("------------ thread B -----------");
                }
            }
        }).start();
    }

    // 拷贝到其他地方使用
    boolean isBind;
    Service myservice;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myservice = ((BaseService.ServiceBinder) service).getService();
            isBind = true;
        }

        /**
         * Android系统在与Service的连接意外丢失时调用这个
         * 当service崩溃了或被强杀了
         * 当客户端解除绑定时，这个方法不会被调用
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            myservice = null;
        }
    };

    void bindService() {
        if (!isBind && myservice == null) {
            Intent intent = new Intent(this, BaseService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    void unbindService() {
        if (isBind) {
            unbindService(serviceConnection);
            myservice = null;
            isBind = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }
}
