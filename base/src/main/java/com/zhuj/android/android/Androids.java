package com.zhuj.android.android;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import java.util.List;

public class Androids {
    private Resources resources;

    private Androids(Application app) {
        resources = app.getResources();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSystemService(Context context, String serviceName) {
        return (T) context.getSystemService(serviceName);
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