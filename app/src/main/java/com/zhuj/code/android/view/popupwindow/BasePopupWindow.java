package com.zhuj.code.android.view.popupwindow;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


public abstract class BasePopupWindow extends PopupWindow {
    private Button confirm;
    private Button cancel;
    private TextView title;
    private ImageView iconContent;
    private ImageView iconCancel;
    private TextView content;

    public void show(View parent, int x, int y) {
        if (!isShowing())
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        else
            dismiss();
    }

    public interface ICallBack {
        void onConfirm();

        void onCancel();
    }

    private ICallBack callBack;

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }
}
