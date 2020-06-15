package com.zhuj.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhuj.android.database.DBConfig;
import com.zhuj.android.database.room.AndroidDatabase;

import java.util.List;

public class App extends Application {
    private static App instance;

    private AndroidDatabase roomDB;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //是否选择显示线程信息，默认为true
                .methodCount(1)         //方法数显示多少行，默认2行
                .tag("App")   //自定义TAG全部标签，默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        roomDB = Room.databaseBuilder(this, AndroidDatabase.class, DBConfig.ROOM_DB_NAME).build();
    }

    public static App getInstance() {
        return instance;
    }

    public AndroidDatabase db() {
        return roomDB;
    }


    /*
     * App默认开启一个进程，进程名就是AndroidManifest.xml文件中项目的包名
     * 多进程，Application的onCreate方法被多次执行
     */
    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
            if (runningApps != null) {
                for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
                    if (proInfo.pid == android.os.Process.myPid()) {
                        if (proInfo.processName != null) {
                            return proInfo.processName;
                        }
                    }
                }
            }
        }
        return null;
    }
}
