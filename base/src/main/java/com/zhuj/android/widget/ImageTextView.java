package com.zhuj.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zhuj.android.base.R;

public class ImageTextView extends AppCompatTextView {
    // private Drawable drawableLeft, drawableTop, drawableRight, drawableBottom;
    private int drawableWidth, drawableHeight;

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ImageTextView, 0, 0);
        drawableWidth = ta.getDimensionPixelSize(R.styleable.ImageTextView_drawableWidth, 0);
        drawableHeight = ta.getDimensionPixelSize(R.styleable.ImageTextView_drawableHeight, 0);
        ta.recycle();

        if (drawableWidth != 0 || drawableHeight != 0) {
            Drawable[] drawables = getCompoundDrawablesRelative();
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    int width = drawableWidth != 0 ? drawableWidth : drawable.getIntrinsicWidth();
                    int height = drawableHeight != 0 ? drawableHeight : drawable.getIntrinsicHeight();
                    drawable.setBounds(0, 0, width, height);
                }
            }
            setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
