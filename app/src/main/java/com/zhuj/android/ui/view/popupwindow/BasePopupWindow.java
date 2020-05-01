package com.zhuj.android.ui.view.popupwindow;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;

public class BasePopupWindow extends PopupWindow {

    public BasePopupWindow(Context context, @LayoutRes int layoutId ) {
        View contentView = LayoutInflater.from(context).inflate(layoutId,null);
        setContentView(contentView);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
    }

    public interface ConfirmListener {
        void onConfirm(View view);

    }

    public interface CancelListener {
        void onCancel();
    }

}
