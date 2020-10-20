package com.zhuj.android.base.dialog;

import android.view.View;

public abstract class BaseDialogFragment extends IDialogFragment {
    /**
     * 给Activity 监控 Dialog 的 View Click事件
     */
    protected View.OnClickListener callbackListener;

    protected void setCallbackListener(View.OnClickListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    protected void addClickCallback(View... views) {
        for (View view : views) {
            view.setOnClickListener(callbackListener);
        }
    }

    protected void addClickCallback(int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(callbackListener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    protected void addClickListener(View.OnClickListener listener, int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }
}
