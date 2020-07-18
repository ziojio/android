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
    protected final String TAG = getClass().getSimpleName();

    /**
     * 自己创建 Dialog 时, return 0, 并且 Override onCreateDialog
     *
     * @return 布局id
     */
    protected abstract int layoutId();

    protected abstract void initView();

    /**
     * 设置 window
     * 1. 可以直接代码设置窗口样式
     * WindowManager.LayoutParams lp = window.getAttributes();
     * 2. 由于填充的根为null, 根布局的部分参数失效，在xml文件多嵌套一个布局
     * 使用CardView作为dialog的容器
     */
    protected abstract void windowBehavior();

    public IDialogFragment() {
        setStyle(STYLE_NO_TITLE, 0);
    }

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
        initView();
        windowBehavior();
    }

    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    public void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), TAG);
    }

}
