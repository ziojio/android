package com.zhuj.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zhuj.android.base.R;

public class TextView extends AppCompatTextView {

    public TextView(Context context) {
        this(context, null, 0);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TextView, 0, 0);
        setCompoundDrawablesFromAttributeSet(ta);
        ta.recycle();
    }

    private void setCompoundDrawablesFromAttributeSet(final TypedArray ta) {
        int drawableWidth = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawableWidth, 0);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawableHeight, 0);

        int drawablePaddingStart = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawablePaddingStart, 0);
        int drawablePaddingTop = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawablePaddingTop, 0);
        int drawablePaddingEnd = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawablePaddingEnd, 0);
        int drawablePaddingBottom = ta.getDimensionPixelSize(R.styleable.TextView_zui_drawablePaddingBottom, 0);

        if (drawableWidth == 0 && drawableHeight == 0
                && drawablePaddingStart == 0 && drawablePaddingEnd == 0
                && drawablePaddingTop == 0 && drawablePaddingBottom == 0) {
            return;
        }
        Drawable[] drawables = getCompoundDrawablesRelative();
        boolean isChanged = false;
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                int width = drawableWidth != 0 ? drawableWidth : drawable.getIntrinsicWidth();
                int height = drawableHeight != 0 ? drawableHeight : drawable.getIntrinsicHeight();
                int x = drawablePaddingStart - drawablePaddingEnd;
                int y = drawablePaddingTop - drawablePaddingBottom;
                drawable.setBounds(x, y, width + x, height + y);
                isChanged = true;
            }
        }
        if (isChanged) {
            setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }
}
