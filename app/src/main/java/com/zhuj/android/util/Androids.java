package com.zhuj.android.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public final class Androids {
    private static final String TAG = Androids.class.getSimpleName();

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkNormal(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(TAG, "current network name：" + name);
            return true;
        } else {
            Log.i(TAG, "no network");
            return false;
        }
    }


}
