package com.zhuj.android.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
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

    protected abstract void initData();

    protected abstract void initEvent();

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
        initData();
        initEvent();
        windowBehavior();
    }

    protected <T extends View> T findViewById(int id) {
        return requireView().findViewById(id);
    }

    public void show(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), TAG);
    }

    /**
     * This method will be called after {@link #onCreate(Bundle)} and before {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * DialogFragment own the {@link Dialog#setOnCancelListener } and {@link Dialog#setOnDismissListener} callbacks.
     * You must not set them yourself. override {@link #onCancel(DialogInterface)} and {@link #onDismiss(DialogInterface)}.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * 设置外部回调方法，
     * 覆写DialogFragment {@link DialogFragment#onCancel(DialogInterface)}
     * and {@link DialogFragment#onDismiss(DialogInterface)}
     */
    protected DialogInterface.OnCancelListener onCancelListener;

    protected DialogInterface.OnDismissListener onDismissListener;

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        this.onCancelListener = cancelListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.onDismissListener = dismissListener;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (this.onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
