package com.zhuj.android.base.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class BaseService extends Service {
    private final String TAG = getClass().getSimpleName();

    public class ServiceBinder extends Binder {
        public Service getService() {
            return BaseService.this;
        }
    }

    private ServiceBinder binder = new ServiceBinder();

    protected boolean isAllowBind() {
        return true;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: intent Action=" + intent.getAction());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: isAllowBind=" + isAllowBind());
        return isAllowBind() ? binder : null;
    }

    // 拷贝到其他地方使用
    // 1. Activity
    private void bindService() {
        Intent intent = new Intent(this,  getClass());
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
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
}


