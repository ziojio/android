package com.zhuj.android.base.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.zhuj.android.base.BuildConfig;


public abstract class IDialogFragment extends DialogFragment {
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     *
     * @return 布局id
     */
    protected abstract int layoutId();

    /**
     * 设置 window
     */
    protected abstract void windowBehavior();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() != 0) {
            return inflater.inflate(layoutId(), container, false);
        }
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart: init window behavior");
        }
        windowBehavior();
    }

    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    protected void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), TAG);
    }

}
