package com.zhuj.android.zui.widget.round;

import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.Nullable;

public class RoundButtonDrawable extends GradientDrawable {
    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean isAutoAdjustRoundSize = false;
    private float mRadius;
    private ColorStateList mBackground;
    private int mStrokeWidth = 0;
    private ColorStateList mStrokeColors;

    @Override
    public void setColor(@Nullable ColorStateList colorStateList) {
        mBackground = colorStateList;
        super.setColor(colorStateList);
    }

    @Override
    public void setStroke(int width, @Nullable ColorStateList colors) {
        mStrokeWidth = width;
        mStrokeColors = colors;
        super.setStroke(width, colors);
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeColors(@Nullable ColorStateList colors) {
        setStroke(mStrokeWidth, colors);
    }

    public boolean isAutoAdjustRoundSize() {
        return isAutoAdjustRoundSize;
    }

    public void setAutoAdjustRoundSize(boolean autoAdjustRoundSize) {
        isAutoAdjustRoundSize = autoAdjustRoundSize;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        boolean superRet = super.onStateChange(stateSet);
        if (mBackground != null) {
            int color = mBackground.getColorForState(stateSet, 0);
            setColor(color);
            superRet = true;
        }
        if (mStrokeColors != null) {
            int color = mStrokeColors.getColorForState(stateSet, 0);
            setStroke(mStrokeWidth, color);
            superRet = true;
        }
        return superRet;
    }

    @Override
    public boolean isStateful() {
        return (mBackground != null && mBackground.isStateful())
                || (mStrokeColors != null && mStrokeColors.isStateful())
                || super.isStateful();
    }

    @Override
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (isAutoAdjustRoundSize) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2.f);
        }
    }

    @Override
    public void setCornerRadius(float radius) {
        mRadius = radius;
        super.setCornerRadius(radius);
    }
}
