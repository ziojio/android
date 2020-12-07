package com.zhuj.android.zui.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.zhuj.android.zui.R;


/**
 * 设置不滑动的最大的高度，小于最大高度时包裹内容，不滑动
 */
public class ZUINestedScrollView extends NestedScrollView {
    private int maxHeight;

    public ZUINestedScrollView(Context context) {
        this(context, null);
    }

    public ZUINestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUINestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUINestedScrollView, 0, 0);
        maxHeight = ta.getDimensionPixelSize(R.styleable.ZUINestedScrollView_maxHeight, 0);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}