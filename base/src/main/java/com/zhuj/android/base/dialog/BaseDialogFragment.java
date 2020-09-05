package com.zhuj.android.base.dialog;

import android.view.View;

public abstract class BaseDialogFragment extends IDialogFragment implements View.OnClickListener {
    /**
     * 给Activity 监控 Dialog 的 View Click事件
     */
    protected View.OnClickListener callbackListener;

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

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

    protected void addClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    protected void addClick(int... viewIds) {
        for (int id : viewIds) {
            findViewById(id).setOnClickListener(this);
        }
    }
}
