package com.zhuj.android.util.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import java.util.Arrays;
import java.util.List;

public class Permissions {
    /**
     * 是否是6.0以上版本
     */
    public  static boolean isOverMarshmallowApi23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否是8.0以上版本
     */
    public static boolean isOverOreoApi26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 返回应用程序在清单文件中注册的权限
     */
    public  static List<String> getManifestPermissions(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            return Arrays.asList(pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检测权限有没有在清单文件中注册
     *
     * @param activity           Activity对象
     * @param requestPermissions 请求的权限组
     */
    public static void checkRegisterPermissions(Activity activity, List<String> requestPermissions) {
        List<String> manifest = getManifestPermissions(activity);
        if (manifest != null && manifest.size() != 0) {
            for (String permission : requestPermissions) {
                if (!manifest.contains(permission)) {
                    throw new RuntimeException("you must add this permission:" + permission + " to AndroidManifest");
                }
            }
        }
    }

    /**
     * 是否有安装权限
     */
    public static boolean isHasInstallPermission(Context context) {
        if (isOverOreoApi26()) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    /**
     * 是否有悬浮窗权限
     */
    public static boolean isHasOverlaysPermission(Context context) {
        if (isOverMarshmallowApi23()) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

}