package zhuj.android.base.activityresult;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import zhuj.function.FunctionI;

public class ActivityResultProxy {

    private static final String TAG = "ACTIVITY_RESULT_FRAGMENT";

    private final WeakReference<FragmentActivity> activityReference;

    private final List<ActivityResultListener> resultListeners = new ArrayList<>();

    private final ActivityResultListener fragmentActivityResultListener = new ActivityResultListener() {
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            for (ActivityResultListener listener : resultListeners) {
                listener.onActivityResult(requestCode, resultCode, data);
            }
        }
    };

    public static ActivityResultProxy startForResult(final FragmentActivity activity, @Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        return new ActivityResultProxy(activity).startForResult(intent, listener);
    }

    public static ActivityResultProxy startForResult(final Fragment fragment, @Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        return new ActivityResultProxy(fragment).startForResult(intent, listener);
    }

    public ActivityResultProxy(@Nullable final FragmentActivity activity) {
        this.activityReference = new WeakReference<>(activity);
    }

    public ActivityResultProxy(@Nullable final Fragment fragment) {
        FragmentActivity activity = null;
        if (fragment != null) {
            activity = fragment.requireActivity();
        }
        this.activityReference = new WeakReference<>(activity);
    }

    public ActivityResultProxy startForResult(@Nullable final Intent intent, @Nullable final ActivityResultListener listener) {
        if (intent != null && listener != null) {
            this.resultListeners.add(listener);
            this.startActivityForResult(intent);
        }
        return this;
    }

    public ActivityResultProxy onActivityResult(@Nullable final ActivityResultListener resultListener) {
        if (resultListener != null) {
            this.resultListeners.add(resultListener);
        }
        return this;
    }

    public ActivityResultProxy onActivityResultOk(@Nullable final FunctionI<Intent> function) {
        if (function != null) {
            this.resultListeners.add(new ActivityResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (resultCode == Activity.RESULT_OK) {
                        function.apply(data);
                    }
                }
            });
        }
        return this;
    }

    public ActivityResultProxy onActivityResultCancel(@Nullable final FunctionI<Intent> function) {
        if (function != null) {
            this.resultListeners.add(new ActivityResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (resultCode == Activity.RESULT_CANCELED) {
                        function.apply(data);
                    }
                }
            });
        }
        return this;
    }

    private void startActivityForResult(@NonNull final Intent intent) {
        final FragmentActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        final ActivityResultFragment oldFragment = (ActivityResultFragment) activity
                .getSupportFragmentManager()
                .findFragmentByTag(TAG);

        if (oldFragment != null) {
            // 设置监听器， 似乎没有用
            oldFragment.setActivityResultListener(fragmentActivityResultListener);
        } else {
            final ActivityResultFragment newFragment = ActivityResultFragment.newInstance(intent);
            newFragment.setActivityResultListener(fragmentActivityResultListener);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(newFragment, TAG)
                            .commitNowAllowingStateLoss();
                }
            });
        }
    }
}
