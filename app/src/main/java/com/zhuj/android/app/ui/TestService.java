package com.zhuj.android.app.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;


import com.zhuj.android.remote.CallBack;
import com.zhuj.android.remote.RemoteService;


public class TestService extends Service {
    RemoteService service;

    @Override
    public void onCreate() {
        super.onCreate();

        service = new RemoteService.Stub() {
            @Override
            public Intent syncFunction(String funcName, Intent data) throws RemoteException {
                return null;
            }

            @Override
            public void asyncFunction(String funcName, Intent data, CallBack callback) throws RemoteException {
                callback.onResult(10, new Intent());
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return service.asBinder();
    }
}
