package zhuj.android.utils.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import zhuj.android.utils.Androids;

public class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    public interface OnAppStatusChangedListener {
        void onForeground();

        void onBackground();
    }

    public interface OnActivityDestroyedListener {
        void onActivityDestroyed(Activity activity);
    }

    final LinkedList<Activity> mActivityList = new LinkedList<>();
    final Map<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
    final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();

    private int mForegroundCount = 0;
    private int mConfigCount = 0;
    private boolean mIsBackground = false;

    private static void fixSoftInputLeaks(final Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) Androids.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] leakViews = new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"};
        for (String leakView : leakViews) {
            try {
                Field leakViewField = InputMethodManager.class.getDeclaredField(leakView);
                if (leakViewField == null) {
                    continue;
                }
                if (!leakViewField.isAccessible()) {
                    leakViewField.setAccessible(true);
                }
                Object obj = leakViewField.get(imm);
                if (!(obj instanceof View)) {
                    continue;
                }
                View view = (View) obj;
                if (view.getRootView() == activity.getWindow().getDecorView().getRootView()) {
                    leakViewField.set(imm, null);
                }
            } catch (Throwable ignore) { /**/ }
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        setTopActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!mIsBackground) {
            setTopActivity(activity);
        }
        if (mConfigCount < 0) {
            ++mConfigCount;
        } else {
            ++mForegroundCount;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        setTopActivity(activity);
        if (mIsBackground) {
            mIsBackground = false;
            postStatus(true);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            --mConfigCount;
        } else {
            --mForegroundCount;
            if (mForegroundCount <= 0) {
                mIsBackground = true;
                postStatus(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {/**/}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
        consumeOnActivityDestroyedListener(activity);
        fixSoftInputLeaks(activity);
    }

    Activity getTopActivity() {
        if (!mActivityList.isEmpty()) {
            final Activity topActivity = mActivityList.getLast();
            if (topActivity != null) {
                return topActivity;
            }
        }
        Activity topActivityByReflect = getTopActivityByReflect();
        if (topActivityByReflect != null) {
            setTopActivity(topActivityByReflect);
        }
        return topActivityByReflect;
    }

    private void setTopActivity(final Activity activity) {
        if (mActivityList.contains(activity)) {
            if (!mActivityList.getLast().equals(activity)) {
                mActivityList.remove(activity);
                mActivityList.addLast(activity);
            }
        } else {
            mActivityList.addLast(activity);
        }
    }

    void addOnAppStatusChangedListener(final Object object,
                                       final OnAppStatusChangedListener listener) {
        mStatusListenerMap.put(object, listener);
    }

    void removeOnAppStatusChangedListener(final Object object) {
        mStatusListenerMap.remove(object);
    }

    void removeOnActivityDestroyedListener(final Activity activity) {
        if (activity == null) {
            return;
        }
        mDestroyedListenerMap.remove(activity);
    }

    void addOnActivityDestroyedListener(final Activity activity,
                                        final OnActivityDestroyedListener listener) {
        if (activity == null || listener == null) {
            return;
        }
        Set<OnActivityDestroyedListener> listeners;
        if (!mDestroyedListenerMap.containsKey(activity)) {
            listeners = new HashSet<>();
            mDestroyedListenerMap.put(activity, listeners);
        } else {
            listeners = mDestroyedListenerMap.get(activity);
            if (listeners.contains(listener)) {
                return;
            }
        }
        listeners.add(listener);
    }

    private void postStatus(final boolean isForeground) {
        if (mStatusListenerMap.isEmpty()) {
            return;
        }
        for (OnAppStatusChangedListener onAppStatusChangedListener : mStatusListenerMap.values()) {
            if (onAppStatusChangedListener == null) {
                return;
            }
            if (isForeground) {
                onAppStatusChangedListener.onForeground();
            } else {
                onAppStatusChangedListener.onBackground();
            }
        }
    }

    private void consumeOnActivityDestroyedListener(Activity activity) {
        Iterator<Map.Entry<Activity, Set<OnActivityDestroyedListener>>> iterator
                = mDestroyedListenerMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry = iterator.next();
            if (entry.getKey() == activity) {
                Set<OnActivityDestroyedListener> value = entry.getValue();
                for (OnActivityDestroyedListener listener : value) {
                    listener.onActivityDestroyed(activity);
                }
                iterator.remove();
            }
        }
    }

    private Activity getTopActivityByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
            mActivityListField.setAccessible(true);
            Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
            if (activities == null) {
                return null;
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity) activityField.get(activityRecord);
                }
            }
        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}