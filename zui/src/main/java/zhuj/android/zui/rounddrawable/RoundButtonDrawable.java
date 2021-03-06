package zhuj.android.zui.rounddrawable;

import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.Nullable;

public class RoundButtonDrawable extends GradientDrawable {
    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean isAutoAdjustRoundSize = false;
    private int mStrokeWidth = 0;
    private ColorStateList mStrokeColors;

    @Override
    public void setStroke(int width, @Nullable ColorStateList colors) {
        mStrokeWidth = width;
        mStrokeColors = colors;
        super.setStroke(width, colors);
    }

    public void setStrokeWidth(int mStrokeWidth) {
        setStroke(mStrokeWidth, mStrokeColors);
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public ColorStateList getStrokeColors() {
        return mStrokeColors;
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
    protected void onBoundsChange(Rect r) {
        super.onBoundsChange(r);
        if (isAutoAdjustRoundSize) {
            // 修改圆角为短边的一半
            setCornerRadius(Math.min(r.width(), r.height()) / 2f);
        }
    }

}
