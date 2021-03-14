package zhuj.android.zui.drawable.round;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

public class RoundDrawable extends GradientDrawable {
    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private boolean isAutoAdjustRoundSize = false;

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
