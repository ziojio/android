package com.jbzh.android.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jbzh.android.view.R;


public class MicTipsPopupWindow extends PopupWindow {

    private ImageView iconView;
    private TextView tipsText;

    public MicTipsPopupWindow(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout._popupwindow_mic_tips, null);
        setContentView(view);
        iconView = view.findViewById(R.id.mic_icon_tips);
        tipsText = view.findViewById(R.id.mic_text_tips);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void show(View parent) {
        int[] p = new int[2];
        parent.getLocationOnScreen(p);
        showAtLocation(parent, Gravity.NO_GRAVITY, p[0] + 100, p[1] - 20);
    }

    public void setMicEnable(boolean micEnable) {
        if (micEnable) {
            tipsText.setTextColor(Color.rgb(183, 183, 185));
            tipsText.setText("打开");
            iconView.setBackgroundResource(R.drawable.icon_mic_gray);
        } else {
            tipsText.setTextColor(Color.rgb(255, 59, 59));
            tipsText.setText("关闭");
            iconView.setBackgroundResource(R.drawable.icon_mic_red);
        }
    }
}
