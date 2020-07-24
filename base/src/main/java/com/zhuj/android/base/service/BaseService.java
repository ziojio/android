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
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart: ");
        super.onStart(intent, startId);
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

    @Override
    public boolean onUnbind(Intent intent) {
        boolean bool = super.onUnbind(intent);
        Log.d(TAG, "onUnbind: return=" + bool);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ");
        super.onRebind(intent);
    }

    // 拷贝到其他地方使用
    boolean isBind;
    Service myservice;

    private void bindService() {
        if (!isBind && myservice == null) {
            Intent intent = new Intent(this, BaseService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindService() {
        if (isBind) {
            unbindService(serviceConnection);
            myservice = null;
            isBind = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
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
}

