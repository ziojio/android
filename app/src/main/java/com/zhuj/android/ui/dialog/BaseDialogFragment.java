package com.zhuj.android.ui.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
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
    protected abstract void initWindow();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() != 0) {
            return inflater.inflate(layoutId(), container, false);
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: init window behavior");
        initWindow();
    }


    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    public <T extends FragmentActivity> void show(T fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), TAG);
    }

    public void addClick(View v) {
        v.setOnClickListener(this);
    }
}
