package com.zhuj.android.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.zhuj.android.ICallBack;
import com.zhuj.android.IRemoteService;

import java.security.Provider;

public class TestService extends Service {
    IRemoteService service;

    @Override
    public void onCreate() {
        super.onCreate();

        service = new IRemoteService.Stub() {
            @Override
            public Intent syncFunction(String funcName, Intent data) throws RemoteException {
                return null;
            }

            @Override
            public void asyncFunction(String funcName, Intent data, ICallBack callback) throws RemoteException {
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
