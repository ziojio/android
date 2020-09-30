package com.zhuj.android.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import com.zhuj.android.helper.ActivityLifecycleHelper;
import com.zhuj.android.logger.Logger;
import com.zhuj.android.thread.MainExecutor;
import com.zhuj.android.thread.WorkExecutor;

import java.util.List;

public class Androids {

    private static Application App;
    private static ActivityLifecycleHelper activityLifecycleHelper;

    /**
     * 主线程Handler
     */
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void initialize(Application app) {
        App = app;
    }

    public static void initialize(Activity activity) {
        App = activity.getApplication();
    }

    public static Context getContext() {
        testInitialize();
        return App;
    }

    private static void testInitialize() {
        if (App == null) {
            throw new ExceptionInInitializerError("请先调用 Androids.initialize(Application) 初始化！");
        }
    }

    /**
     * 设置日志记录
     */
    public static void debug(boolean isDebug) {
        Logger.debug(isDebug);
        ((Application) Androids.getContext()).registerActivityLifecycleCallbacks(getActivityLifecycleHelper());

        // if (isDebug) {
        //
        // }
    }

    /**
     * Returns true if the calling thread is the main thread.
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 获取主线程的Handler
     *
     * @return 主线程Handler
     */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }

    public static ActivityLifecycleHelper getActivityLifecycleHelper() {
        if (activityLifecycleHelper == null) {
            activityLifecycleHelper = new ActivityLifecycleHelper();
        }
        return activityLifecycleHelper;
    }

    private static MainExecutor mainExecutor;

    public static MainExecutor getMainExecutor() {
        if (mainExecutor == null) {
            mainExecutor = new MainExecutor();
        }
        return mainExecutor;
    }

    /**
     * 在主线程中执行
     *
     * @param runnable
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return getMainHandler().post(runnable);
    }

    public static boolean runOnUiThread(Runnable run, long ms) {
        return getMainHandler().postDelayed(run, ms);
    }

    private static WorkExecutor workExecutor;

    public static WorkExecutor getWorkExecutor() {
        if (workExecutor == null) {
            workExecutor = new WorkExecutor();
        }
        return workExecutor;
    }

    public static void runOnWorkThread(Runnable runnable) {
        getWorkExecutor().execute(runnable);
    }

    public static void runOnWorkThread(Runnable runnable, long ms) {
        getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnWorkThread(runnable);
            }
        }, ms);
    }

    /**
     * 获取全局ContentResolver
     *
     * @return ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    /**
     * 获取全局资源
     *
     * @return 全局资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取全局Asset管理
     *
     * @return 全局Asset管理
     */
    public static AssetManager getAssetManager() {
        return getContext().getAssets();
    }

    /**
     * 获取包管理
     *
     * @return 包管理
     */
    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /**
     * 避免 NullPointException 的提示
     *
     * @param serviceName Context @ServiceName 注解值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSystemService(String serviceName) {
        Object obj = getContext().getSystemService(serviceName);
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSystemService(Context context, String name, Class<T> clazz) {
        Object obj = context.getSystemService(name);
        if (clazz.isInstance(obj)) {
            return (T) obj;
        }
        return null;
    }

    /*
     * App默认开启一个进程，进程名就是AndroidManifest.xml文件中项目的包名
     * 多进程，Application的onCreate方法会被多次执行
     */
    private static String getProcessName(Context context) {
        ActivityManager am = getSystemService(context, Context.ACTIVITY_SERVICE, ActivityManager.class);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am != null ? am.getRunningAppProcesses() : null;
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
                if (proInfo.pid == android.os.Process.myPid()) {
                    if (proInfo.processName != null) {
                        return proInfo.processName;
                    }
                }
            }
        }
        return null;
    }
}