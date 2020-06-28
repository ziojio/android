package com.zhuj.android;

import android.app.Application;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhuj.android.data.database.room.AndroidDatabase;

public class App extends Application {
    private static App instance;

    private volatile AndroidDatabase roomDB;

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

        roomDB = AndroidDatabase.getInstance(this);
    }

    public static App getInstance() {
        return instance;
    }

    public AndroidDatabase db() {
        return roomDB;
    }

}
