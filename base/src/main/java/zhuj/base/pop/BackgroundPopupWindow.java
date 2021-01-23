package zhuj.base.pop;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.zhuj.code.Preconditions;

import zhuj.base.R;
import zui.widget.TriangleView;

public class BackgroundPopupWindow extends BasePopupWindow {
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private static final int BG_LAYOUT_RES = R.layout.pop_base_background;
    private int contentLayoutRes;

    private LinearLayout viewContainer;
    private ConstraintLayout rootView;
    private TriangleView triangleView;

    protected int triangleWidth;
    protected int triangleHeight;
    protected int triangleOffset;

    protected int direction;
    protected int color;

    public void setBackgroundColor(int color) {
        this.color = color;
        triangleView.setColor(color);
        viewContainer.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setTriangleOffset(int triangleOffset) {
        this.triangleOffset = triangleOffset;
    }

    public void setTriangleLayoutParams(int width, int height) {
        triangleWidth = width;
        triangleHeight = height;
    }

    public void initLayout() {
        ConstraintSet set = new ConstraintSet();
        set.clone(rootView);
        int triangleId = R.id.triangleView;
        int containerId = R.id.view_container;
        if (triangleWidth != 0 && triangleHeight != 0) {
            set.constrainWidth(triangleId, triangleWidth);
            set.constrainHeight(triangleId, triangleHeight);
        } else {
            set.applyToLayoutParams(triangleId, new ConstraintLayout.LayoutParams(-2, -2));
        }
        set.applyToLayoutParams(containerId, new ConstraintLayout.LayoutParams(-2, -2));
        if (direction == TOP || direction == BOTTOM) {
            set.connect(containerId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
            set.connect(containerId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

            if (direction == TOP) {
                set.connect(containerId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                set.connect(triangleId, ConstraintSet.TOP, containerId, ConstraintSet.BOTTOM);
            } else {
                set.connect(triangleId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                set.connect(containerId, ConstraintSet.TOP, triangleId, ConstraintSet.BOTTOM);
            }

            if (triangleOffset == 0) {
                set.centerHorizontally(triangleId, ConstraintSet.PARENT_ID);
            } else {
                set.connect(triangleId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.setMargin(triangleId, ConstraintSet.START, triangleOffset);
            }
        } else if (direction == LEFT || direction == RIGHT) {
            set.connect(containerId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
            set.connect(containerId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            if (direction == LEFT) {
                set.connect(containerId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.connect(triangleId, ConstraintSet.START, containerId, ConstraintSet.END);
            } else {
                set.connect(triangleId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.connect(containerId, ConstraintSet.START, triangleId, ConstraintSet.END);
            }

            if (triangleOffset == 0) {
                set.centerVertically(triangleId, ConstraintSet.PARENT_ID);
            } else {
                set.connect(triangleId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.START);
                set.setMargin(triangleId, ConstraintSet.TOP, triangleOffset);
            }
        }
        set.applyTo(rootView);
    }

    public BackgroundPopupWindow(Context context, int layoutId) {
        super(context, BG_LAYOUT_RES);
        Preconditions.checkArgument(layoutId != 0);
        contentLayoutRes = layoutId;

        rootView = findViewById(R.id.rootView);
        viewContainer = findViewById(R.id.view_container);
        triangleView = findViewById(R.id.triangleView);

        LayoutInflater.from(getContext()).inflate(contentLayoutRes, viewContainer, true);
        viewContainer.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // Logger.e("Width: " + viewContainer.getMeasuredWidth() + ", height: " + viewContainer.getMeasuredHeight());
    }

    void initTriangleView() {
        int triangleDirection;
        if (direction == TOP) {
            triangleDirection = BOTTOM;
        } else if (direction == BOTTOM) {
            triangleDirection = TOP;
        } else if (direction == LEFT) {
            triangleDirection = RIGHT;
        } else if (direction == RIGHT) {
            triangleDirection = LEFT;
        } else {
            throw new IllegalStateException("direction is error");
        }
        triangleView.setDirection(triangleDirection);
    }

    private void measureWidthHeight() {
        rootView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mPopupWidth = rootView.getMeasuredWidth();
        mPopupHeight = rootView.getMeasuredHeight();
    }

    @Override
    public void showUpViewCenter(View view) {
        setDirection(TOP);
        initTriangleView();
        initLayout();
        measureWidthHeight();
        super.showUpViewCenter(view);
    }

    @Override
    public void showDownViewCenter(View view) {
        setDirection(BOTTOM);
        initTriangleView();
        initLayout();
        measureWidthHeight();
        super.showDownViewCenter(view);
    }

}
