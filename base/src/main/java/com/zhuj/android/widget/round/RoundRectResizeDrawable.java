package com.zhuj.android.widget.round;

import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.Nullable;

public class RoundRectResizeDrawable extends GradientDrawable {

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
        super.setColor(colorStateList);
        mBackground = colorStateList;
    }

    @Override
    public void setColor(int argb) {
        setColor(ColorStateList.valueOf(argb));
    }

    @Override
    public void setStroke(int width, @Nullable ColorStateList colors) {
        super.setStroke(width, colors);
        mStrokeWidth = width;
        mStrokeColors = colors;
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

    private boolean hasNativeStateListAPI() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public void setAutoAdjustRoundSize(boolean autoAdjustRoundSize) {
        isAutoAdjustRoundSize = autoAdjustRoundSize;
    }

    @Override
    protected boolean onStateChange(int[] stateSet) {
        return super.onStateChange(stateSet);
    }

    @Override
    public boolean isStateful() {
        return super.isStateful();
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
        super.setCornerRadius(radius);
        mRadius = radius;
    }
}
