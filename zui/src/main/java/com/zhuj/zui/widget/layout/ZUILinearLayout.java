package com.zhuj.zui.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ZUILinearLayout extends LinearLayout {
    public ZUILinearLayout(Context context) {
        this(context, null, 0);
    }

    public ZUILinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUILinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        // final TypedArray ta = context.getTheme()
        //     .obtainStyledAttributes(attrs, R.styleable.ZUILinearLayout, 0, 0);
        // ta.recycle();
    }

}
