package com.zhuj.android.base.dialog;

import android.view.View;


public abstract class BaseDialogFragment extends IDialogFragment implements View.OnClickListener {

    protected View.OnClickListener callbackListener;

    protected void setCallbackListener(View.OnClickListener callbackListener) {
        this.callbackListener = callbackListener;
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
