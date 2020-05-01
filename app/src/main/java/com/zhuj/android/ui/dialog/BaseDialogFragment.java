package com.zhuj.android.ui.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     * @return 布局id
     */
    protected abstract int layoutId();

    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() != 0) {
            return inflater.inflate(layoutId(), container, false);
        }
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: initView");
        initView();
    }

    /**
     * 在 DialogFragment onActivityCreated() 中，如果创建了 view -> onCreateView
     * dialog 的内容设置为 -> mDialog.setContentView(view)
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: init window behavior");
        Window w = requireDialog().getWindow();
        if (w != null) {
            setWindowBehavior(w);
        } else {
            Log.e(TAG, "onStart: init window behavior, window is null");
        }
    }

    /**
     * 设置默认的 window 行为
     * @param window
     */
    protected void setWindowBehavior(Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度自适应，宽度全屏
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    public <T extends FragmentActivity> void show(T activity) {
        show(activity.getSupportFragmentManager(), TAG);
    }

    public void addViewsClickListener(int[] viewIds, View.OnClickListener listener) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    public void addClick(View v) {
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    }
}
