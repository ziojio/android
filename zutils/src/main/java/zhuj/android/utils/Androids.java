package zhuj.android.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import zhuj.android.utils.helper.ActivityLifecycleHelper;
import zhuj.android.utils.log.Logger;
import zhuj.android.utils.network.NetworkCallback;
import zhuj.android.utils.network.NetworkUtils;

public class Androids {

    private static Application app;
    private static ActivityLifecycleHelper activityLifecycleHelper;
    private static final NetworkCallback networkCallback = new NetworkCallback();
    /**
     * 主线程Handler
     */
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static void initialize(Application app) {
        Androids.app = app;

        NetworkUtils.register(app, networkCallback);
    }

    public static Application getApp() {
        // if (app == null) {
        //     app = getApplicationByReflect();
        //     if (app != null) {
        //         initialize(app);
        //     }
        // }
        testInitialize();
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            return (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void testInitialize() {
        if (app == null) {
            throw new ExceptionInInitializerError("请先调用 Androids.initialize(Application) 初始化！");
        }
    }

    /**
     * 设置日志记录
     */
    public static void debug(boolean isDebug) {
        Logger.debug(isDebug);
    }

    /**
     * 获取主线程的Handler
     *
     * @return 主线程Handler
     */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static ActivityLifecycleHelper getActivityLifecycleCallback() {
        return activityLifecycleHelper;
    }

    public static void registerActivityLifecycleCallback() {
        if (activityLifecycleHelper == null) {
            activityLifecycleHelper = new ActivityLifecycleHelper();
            getApp().registerActivityLifecycleCallbacks(activityLifecycleHelper);
        }
    }

    public NetworkCallback getNetworkCallback() {
        return networkCallback;
    }

    public static boolean isAppForeground() {
        // 利用ActivityLifecycleCallback判断是否在前台，更快捷稳定
        if (activityLifecycleHelper != null) {
            return activityLifecycleHelper.isForeground();
        }
        ActivityManager am = (ActivityManager) getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(getApp().getPackageName());
            }
        }
        return false;
    }

    /**
     * 在主线程中执行
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return getMainHandler().post(runnable);
    }

    private static ThreadPoolExecutor workExecutor;

    public static ThreadPoolExecutor getWorkExecutor() {
        if (workExecutor == null) {
            int CPU_COUNT = Runtime.getRuntime().availableProcessors();
            ThreadFactory THREAD_FACTORY = new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "WorkExecutor Request Thread #" + mCount.getAndIncrement());
                }
            };
            workExecutor = new ThreadPoolExecutor(
                    Math.max(2, Math.min(CPU_COUNT - 1, 4)),
                    CPU_COUNT * 2 + 1,
                    60L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(32),
                    THREAD_FACTORY);
            workExecutor.allowCoreThreadTimeOut(true);
        }
        return workExecutor;
    }

    public static void runOnWorkThread(Runnable runnable) {
        getWorkExecutor().execute(runnable);
    }


    /**
     * 获取全局ContentResolver
     *
     * @return ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return getApp().getContentResolver();
    }

    /**
     * 获取全局资源
     *
     * @return 全局资源
     */
    public static Resources getResources() {
        return getApp().getResources();
    }

    /**
     * 获取全局Asset管理
     *
     * @return 全局Asset管理
     */
    public static AssetManager getAssetManager() {
        return getApp().getAssets();
    }

    /**
     * 获取包管理
     *
     * @return 包管理
     */
    public static PackageManager getPackageManager() {
        return getApp().getPackageManager();
    }

    /**
     * 避免 NullPointException 的提示
     *
     * @param serviceName
     */
    public static <T> T getSystemService(String serviceName) {
        return (T) getApp().getSystemService(serviceName);
    }
}