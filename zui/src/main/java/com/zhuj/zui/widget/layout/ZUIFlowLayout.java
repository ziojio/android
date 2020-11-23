package com.zhuj.zui.widget.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;

import com.zhuj.zui.R;


/**
 * copy from com.google.android.material.internal.FlowLayout
 * Horizontally lay out children until the row is filled and then moved to the next line. Call
 * {@link ZUIFlowLayout#setSingleLine(boolean)} to disable reflow and lay all children out in one line.
 */
public class ZUIFlowLayout extends ViewGroup {
    private int lineSpacing;
    private int itemSpacing;
    private boolean singleLine;

    public ZUIFlowLayout(@NonNull Context context) {
        this(context, null);
    }

    public ZUIFlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUIFlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        singleLine = false;
        loadFromAttributes(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZUIFlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        singleLine = false;
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0);
        lineSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        itemSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
        array.recycle();
    }

    protected int getLineSpacing() {
        return lineSpacing;
    }

    protected void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    protected int getItemSpacing() {
        return itemSpacing;
    }

    protected void setItemSpacing(int itemSpacing) {
        this.itemSpacing = itemSpacing;
    }

    /**
     * Returns whether this chip group is single line or reflowed multiline.
     */
    public boolean isSingleLine() {
        return singleLine;
    }

    /**
     * Sets whether this chip group is single line, or reflowed multiline.
     */
    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        final int maxWidth =
                widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY
                        ? width
                        : Integer.MAX_VALUE;

        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childBottom = childTop;
        int childRight = childLeft;
        int maxChildRight = 0;
        final int maxRight = maxWidth - getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            LayoutParams lp = child.getLayoutParams();
            int leftMargin = 0;
            int rightMargin = 0;
            if (lp instanceof MarginLayoutParams) {
                MarginLayoutParams marginLp = (MarginLayoutParams) lp;
                leftMargin += marginLp.leftMargin;
                rightMargin += marginLp.rightMargin;
            }

            childRight = childLeft + leftMargin + child.getMeasuredWidth();

            // If the current child's right bound exceeds Flowlayout's max right bound and flowlayout is
            // not confined to a single line, move this child to the next line and reset its left bound to
            // flowlayout's left bound.
            if (childRight > maxRight && !isSingleLine()) {
                childLeft = getPaddingLeft();
                childTop = childBottom + lineSpacing;
            }

            childRight = childLeft + leftMargin + child.getMeasuredWidth();
            childBottom = childTop + child.getMeasuredHeight();

            // Updates Flowlayout's max right bound if current child's right bound exceeds it.
            if (childRight > maxChildRight) {
                maxChildRight = childRight;
            }

            childLeft += (leftMargin + rightMargin + child.getMeasuredWidth()) + itemSpacing;

            // For all preceding children, the child's right margin is taken into account in the next
            // child's left bound (childLeft). However, childLeft is ignored after the last child so the
            // last child's right margin needs to be explicitly added to Flowlayout's max right bound.
            if (i == (getChildCount() - 1)) {
                maxChildRight += rightMargin;
            }
        }

        maxChildRight += getPaddingRight();
        childBottom += getPaddingBottom();

        int finalWidth = getMeasuredDimension(width, widthMode, maxChildRight);
        int finalHeight = getMeasuredDimension(height, heightMode, childBottom);
        setMeasuredDimension(finalWidth, finalHeight);
    }

    private static int getMeasuredDimension(int size, int mode, int childrenEdge) {
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(childrenEdge, size);
            default: // UNSPECIFIED:
                return childrenEdge;
        }
    }

    @Override
    protected void onLayout(boolean sizeChanged, int left, int top, int right, int bottom) {
        if (getChildCount() == 0) {
            // Do not re-layout when there are no children.
            return;
        }

        boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        int paddingStart = isRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isRtl ? getPaddingLeft() : getPaddingRight();

        int childStart = paddingStart;
        int childTop = getPaddingTop();
        int childBottom = childTop;
        int childEnd;

        final int maxChildEnd = right - left - paddingEnd;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            LayoutParams lp = child.getLayoutParams();
            int startMargin = 0;
            int endMargin = 0;
            if (lp instanceof MarginLayoutParams) {
                MarginLayoutParams marginLp = (MarginLayoutParams) lp;
                startMargin = MarginLayoutParamsCompat.getMarginStart(marginLp);
                endMargin = MarginLayoutParamsCompat.getMarginEnd(marginLp);
            }

            childEnd = childStart + startMargin + child.getMeasuredWidth();

            if (!singleLine && (childEnd > maxChildEnd)) {
                childStart = paddingStart;
                childTop = childBottom + lineSpacing;
            }

            childEnd = childStart + startMargin + child.getMeasuredWidth();
            childBottom = childTop + child.getMeasuredHeight();

            if (isRtl) {
                child.layout(
                        maxChildEnd - childEnd, childTop, maxChildEnd - childStart - startMargin, childBottom);
            } else {
                child.layout(childStart + startMargin, childTop, childEnd, childBottom);
            }

            childStart += (startMargin + endMargin + child.getMeasuredWidth()) + itemSpacing;
        }
    }
}