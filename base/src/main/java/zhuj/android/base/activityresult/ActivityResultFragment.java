package zhuj.android.base.activityresult;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ActivityResultFragment extends Fragment {

    public static final String START_INTENT = "START_INTENT";

    private static final int REQUEST_CODE = 10086;

    @Nullable
    private ActivityResultListener activityResultListener;

    @Nullable
    private Intent intentToStart;

    public ActivityResultFragment setActivityResultListener(@Nullable ActivityResultListener resultListener) {
        if (activityResultListener != null) {
            this.activityResultListener = resultListener;
        }
        return this;
    }

    public ActivityResultFragment() {
        setRetainInstance(true);
    }

    public static ActivityResultFragment newInstance(@NonNull final Intent intent) {
        Bundle args = new Bundle();
        args.putParcelable(START_INTENT, intent);
        ActivityResultFragment fragment = new ActivityResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.intentToStart = arguments.getParcelable(START_INTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (intentToStart != null) {
            startActivityForResult(intentToStart, REQUEST_CODE);
        } else {
            // this shouldn't happen, but just to be sure
            removeFragment();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (activityResultListener != null) {
                activityResultListener.onActivityResult(requestCode, resultCode, data);
            }
            removeFragment();
        }
    }

    private void removeFragment() {
        getParentFragmentManager().beginTransaction()
                .remove(this)
                .commitAllowingStateLoss();
    }
}