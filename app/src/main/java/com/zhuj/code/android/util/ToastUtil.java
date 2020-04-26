package com.zhuj.code.android.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static boolean isInit = false;
    private static Context mContext;

    public static void showToast(final Activity activity, final String message) {
        if (activity.getMainLooper().isCurrentThread()) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public static void init(Context context) {
        if (isInit) {
            return;
        }
        mContext = context;
        isInit = true;
    }

    private static void confirmInit() {
        if (!isInit) {
            throw new IllegalStateException("ToastUtil 还未初始化!");
        }
    }

    public static void show(String msg) {
        confirmInit();
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(int rid) {
        confirmInit();
        Toast.makeText(mContext, rid, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int rid) {
        confirmInit();
        Toast.makeText(mContext, rid, Toast.LENGTH_LONG).show();
    }

}