package com.zhuj.android.sample;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    private static class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService service = (MyService) iBinder;
        }

        /**
         * Android系统在与 Service 的连接意外丢失时调用, 当 Service 崩溃了 或 被强杀
         * 当客户端解除绑定时，这个方法不会被调用
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
