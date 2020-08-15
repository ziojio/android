package com.zhuj.android.widget.round;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.zhuj.android.base.R;
import com.zhuj.android.widget.helper.AlphaViewHelper;

public class ZUIRoundButton extends AppCompatButton {
    private static final int DEFAULT_RADIUS = 0;

    private AlphaViewHelper mAlphaViewHelper;

    public ZUIRoundButton(@NonNull Context context) {
        this(context, null, 0);
    }

    public ZUIRoundButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUIRoundButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUIRoundButton, 0, 0);
        setBackgroundFromAttributeSet(ta);
        setCompoundDrawablesFromAttributeSet(ta);
        ta.recycle();
    }

    /**
     * 只处理设置的背景颜色
     *
     * @param ta
     */
    private void setBackgroundFromAttributeSet(final TypedArray ta) {
        ColorStateList bgColor = ta.getColorStateList(R.styleable.ZUIRoundButton_zui_backgroundColor);
        if (bgColor == null) {
            return;
        }
        ColorStateList borderColor = ta.getColorStateList(R.styleable.ZUIRoundButton_zui_borderColor);
        int borderWidth = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_borderWidth, 0);

        boolean isAutoAdjustRoundSize = ta.getBoolean(R.styleable.ZUIRoundButton_zui_isAutoAdjustRoundSize, false);
        int radius = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_radius, DEFAULT_RADIUS);
        int radiusTopLeft = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_radiusTopLeft, DEFAULT_RADIUS);
        int radiusTopRight = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_radiusTopRight, DEFAULT_RADIUS);
        int radiusBottomLeft = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_radiusBottomLeft, DEFAULT_RADIUS);
        int radiusBottomRight = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_radiusBottomRight, DEFAULT_RADIUS);

        RoundRectResizeDrawable background = new RoundRectResizeDrawable();

        background.setColor(bgColor);
        if (borderWidth > 0) {
            background.setStroke(borderWidth, borderColor);
        }
        if (radiusTopLeft > 0 || radiusTopRight > 0 || radiusBottomLeft > 0 || radiusBottomRight > 0) {
            float[] radii = new float[]{
                    radiusTopLeft, radiusTopLeft,
                    radiusTopRight, radiusTopRight,
                    radiusBottomRight, radiusBottomRight,
                    radiusBottomLeft, radiusBottomLeft
            };
            background.setCornerRadii(radii);
            isAutoAdjustRoundSize = false;
        } else if (radius > 0) {
            background.setCornerRadius(radius);
            isAutoAdjustRoundSize = false;
        }
        background.setAutoAdjustRoundSize(isAutoAdjustRoundSize);
        setBackground(background);
        // int[] padding = new int[]{getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom()};
        // setPadding(padding[0], padding[1], padding[2], padding[3]);
    }

    private void setCompoundDrawablesFromAttributeSet(final TypedArray ta) {
        int drawableWidth = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawableWidth, 0);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawableHeight, 0);

        int drawablePaddingStart = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawablePaddingStart, 0);
        int drawablePaddingTop = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawablePaddingTop, 0);
        int drawablePaddingEnd = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawablePaddingEnd, 0);
        int drawablePaddingBottom = ta.getDimensionPixelSize(R.styleable.ZUIRoundButton_zui_drawablePaddingBottom, 0);

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

    @Override
    public void setBackgroundColor(int color) {
        Drawable drawable = getBackground();
        if (drawable instanceof RoundRectResizeDrawable) {
            ((RoundRectResizeDrawable) drawable).setColor(color);
        } else {
            RoundRectResizeDrawable background = new RoundRectResizeDrawable();
            background.setColor(color);
            setBackground(background);
        }
    }

    public AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }

}
