package com.zhuj.android.base.dialog;

import android.view.View;


public abstract class BaseCallbackDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    protected View.OnClickListener callbackListener;

    protected void setCallbackListener(View.OnClickListener callbackListener) {
        this.callbackListener = callbackListener;
    }

}
