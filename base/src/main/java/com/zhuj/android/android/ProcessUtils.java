package com.zhuj.android.android;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class ProcessUtils {
    private ProcessUtils(){
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningService = activityManager.getRunningServices(20);
        for (ActivityManager.RunningServiceInfo serviceInfo : runningService) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
