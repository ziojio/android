package com.zhuj.android.base.pop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow {
    private Context context;

    protected abstract int layoutId();

    public BasePopupWindow(Context context) {
        this.context = context;
        setContentView(context, layoutId());
    }

    public void setContentView(Context context, int layoutId) {
        if (layoutId == 0) {
            return;
        }
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        setContentView(view);
    }

    public Context requireContext() {
        if (context == null) {
            throw new IllegalStateException("context is NULL");
        }
        return context;
    }

    public View requireView() {
        View view = getContentView();
        if (view == null) {
            throw new IllegalStateException("content view is NULL");
        }
        return view;
    }

}
