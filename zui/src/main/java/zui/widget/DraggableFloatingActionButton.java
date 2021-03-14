package zui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DraggableFloatingActionButton extends FloatingActionButton {
    private int parentHeight;
    private int parentWidth;

    private int lastX;
    private int lastY;
    private boolean isDrag;

    public DraggableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DraggableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            setPressed(true);
            isDrag = false;
            lastX = rawX;
            lastY = rawY;

            if (getParent() != null) {
                ViewGroup parent = (ViewGroup) getParent();
                parentHeight = parent.getHeight();
                parentWidth = parent.getWidth();
            }
        } else if (action == MotionEvent.ACTION_MOVE) {
            isDrag = parentHeight > 0 && parentWidth > 0;
            //计算手指移动了多少
            int dx = rawX - lastX;
            int dy = rawY - lastY;

            float x = getX() + dx;
            float y = getY() + dy;
            // 检测是否到达边缘 左上右下
            x = x < 0 ? 0 : x > parentWidth - getWidth() ? parentWidth - getWidth() : x;
            y = getY() < 0 ? 0 : getY() + getHeight() > parentHeight ? parentHeight - getHeight() : y;
            setX(x);
            setY(y);
            lastX = rawX;
            lastY = rawY;
           // Log.i("aa", "isDrag=" + isDrag + "getX=" + getX() + ";getY=" + getY() + ";parentWidth=" + parentWidth);
        } else if (action == MotionEvent.ACTION_UP) {
            if (isDrag()) {
                if (rawX >= parentWidth / 2) {
                    // 靠右吸附
                    animate().setInterpolator(new DecelerateInterpolator())
                            .setDuration(500)
                            .xBy(parentWidth - getWidth() - getX())
                            .start();
                } else {
                    // 靠左吸附
                    ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                    oa.setInterpolator(new DecelerateInterpolator());
                    oa.setDuration(500);
                    oa.start();
                }
            }
            // 恢复按压效果
            setPressed(false);
            isDrag = false;
        }
        // 如果是拖拽则消s耗事件，否则正常传递即可
        return isDrag() || super.onTouchEvent(event);
    }

    private boolean isDrag() {
        return isDrag;
    }
}