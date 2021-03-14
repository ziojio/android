package zhuj.android.utils.helper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Stack;

import zhuj.android.utils.log.Logger;

public class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks {
    private final String TAG = getClass().getSimpleName();
    private final Stack<Activity> mActivityStack = new Stack<>();
    private Activity mResumeActivity;

    private int activityCount;
    private final Object mLock = new Object();

    public ActivityLifecycleHelper() {
    }

    /**
     * 判断是否在前台: 判断标准 还存在 start resume 状态 的activity
     */
    public boolean isForeground() {
        return activityCount > 0;
    }

    private String getName(Activity activity) {
        return activity == null ? "NULL" : activity.getLocalClassName();
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        Logger.v(TAG,"[onActivityCreated]: " + getName(activity));
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityCount++;
        Logger.v(TAG,"[onActivityStarted]: " + getName(activity));
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Logger.v(TAG,"[onActivityResumed]: " + getName(activity));
        mResumeActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Logger.v(TAG,"[onActivityPaused]: " + getName(activity));
        mResumeActivity = null;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Logger.v(TAG,"[onActivityStopped]: " + getName(activity));
        activityCount--;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Logger.v(TAG,"[onActivitySaveInstanceState]: " + getName(activity));
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Logger.v(TAG,"[onActivityDestroyed]: " + getName(activity));
        removeActivity(activity);
    }


    /**
     * 添加Activity到堆栈
     */
    private void addActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 将Activity移出堆栈
     *
     * @param activity
     */
    private void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 获取当前应用正在显示的Activity
     */
    public Activity getResumeActivity() {
        return mResumeActivity;
    }

    /**
     * 获取上一个Activity
     *
     * @return 上一个Activity
     */
    public Activity getPreActivity() {
        int size = mActivityStack.size();
        if (size < 2) {
            return null;
        }
        return mActivityStack.elementAt(size - 2);
    }

    /**
     * 某一个Activity是否存在
     *
     * @return true : 存在, false: 不存在
     */
    public boolean isActivityExist(@NonNull Class<? extends Activity> clazz) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        finishActivity(getCurrentActivity());
    }

    /**
     * 结束上一个Activity
     */
    public void finishPreActivity() {
        finishActivity(getPreActivity());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param clazz activity的类
     */
    public void finishActivity(@NonNull Class<? extends Activity> clazz) {
        Iterator<Activity> it = mActivityStack.iterator();
        synchronized (mLock) {
            while (it.hasNext()) {
                Activity activity = it.next();
                if (java.util.Objects.equals(clazz.getCanonicalName(), activity.getClass().getCanonicalName())) {
                    if (!activity.isFinishing()) {
                        it.remove();
                        activity.finish();
                    }
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            Activity activity = mActivityStack.get(i);
            if (activity != null) {
                if (!activity.isFinishing()) {
                    Logger.d("[FinishActivity]:" + getName(activity));
                    activity.finish();
                }
            }
        }
        mActivityStack.clear();
    }

    /**
     * 获取当前Activity的活动栈
     *
     * @return 当前Activity的活动栈
     */
    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    /**
     * 退出
     */
    public void exit() {
        finishAllActivity();
    }
}
