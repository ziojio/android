package com.zhuj.android.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhuj.android.base.R;

public class ZUIScrollView extends ScrollView {
    private int maxHeight;

    public ZUIScrollView(Context context) {
        this(context, null);
    }

    public ZUIScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUIScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUIScrollView, 0, 0);
        maxHeight = ta.getDimensionPixelSize(R.styleable.ZUIScrollView_maxHeight, -1);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}