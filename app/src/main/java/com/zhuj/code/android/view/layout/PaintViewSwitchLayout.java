package com.jbzh.android.layout;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jbzh.android.view.R;

public class PaintViewSwitchLayout extends LinearLayout {
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener callback) {
        onItemClickListener = callback;
    }


    public PaintViewSwitchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayoutItems();
    }

    private TextView pageInfo;

    private void initLayoutItems() {
        LinearLayout rootView = (LinearLayout) getRootView();

        Button leftBtn = new Button(rootView.getContext());
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(85, 85);
        leftBtn.setLayoutParams(params1);
        leftBtn.setBackgroundResource(R.drawable.page_left);
        leftBtn.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, LEFT);
        });
        rootView.addView(leftBtn);

        pageInfo = new TextView(rootView.getContext());
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pageInfo.setLayoutParams(params2);
        pageInfo.setGravity(Gravity.CENTER);
        pageInfo.setText("1/1");
        pageInfo.setTextSize(28);
        pageInfo.setTextColor(Color.parseColor("#222222"));
        rootView.addView(pageInfo);

        Button rightBtn = new Button(rootView.getContext());
        ViewGroup.LayoutParams params3 = new ViewGroup.LayoutParams(85, 85);
        rightBtn.setLayoutParams(params3);
        rightBtn.setBackgroundResource(R.drawable.page_right);
        rightBtn.setOnClickListener(v -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(v, RIGHT);
        });
        rootView.addView(rightBtn);
    }

    public void setPageInfo(String info) {
        if (pageInfo != null)
            pageInfo.setText(info);
    }
}
