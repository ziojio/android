package com.zhuj.android.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;

import com.zhuj.android.android.Displays;
import com.zhuj.android.base.R;
import com.zhuj.android.logger.Logger;

public class GridLayout extends ViewGroup {

    public GridLayout(Context context) {
        this(context, null);
    }

    public GridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0);
        array.recycle();
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
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // ViewGroup.LayoutParams lp = child.getLayoutParams();
            // int startMargin = 0;
            // int endMargin = 0;
            // if (lp instanceof MarginLayoutParams) {
            //     MarginLayoutParams marginLp = (MarginLayoutParams) lp;
            //     startMargin = MarginLayoutParamsCompat.getMarginStart(marginLp);
            //     endMargin = MarginLayoutParamsCompat.getMarginEnd(marginLp);
            // }
        }
        int finalWidth = width;
        int finalHeight = height;
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d("int l=%s, int t=%s, int r=%s, int b=%s", l, t, r, b);
        Displays.logDisplayMetrics(getContext());

        if (getChildCount() == 0) {
            // Do not re-layout when there are no children.
            return;
        }

        boolean isRtl = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
        int paddingStart = isRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isRtl ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        /*
         * 子View在右边最大能达到的位置
         */
        final int maxChildEnd = r - l - paddingEnd;
        final int maxChildBottom = b - t - paddingBottom;

        /*
         * 每一个子View的位置
         */
        int childStart = paddingStart;
        int childTop = paddingTop;
        int childBottom;
        int childEnd;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            ViewGroup.LayoutParams lp = child.getLayoutParams();
            int startMargin = 0;
            int endMargin = 0;
            int topMargin = 0;
            int bottomMargin = 0;
            if (lp instanceof LayoutParams) {
                LayoutParams marginLp = (LayoutParams) lp;
                startMargin = MarginLayoutParamsCompat.getMarginStart(marginLp);
                endMargin = MarginLayoutParamsCompat.getMarginEnd(marginLp);
                topMargin = marginLp.topMargin;
                bottomMargin = marginLp.bottomMargin;

                Logger.d("width=%s, height=%s ", marginLp.width, marginLp.height);
                Logger.d("topMargin=%s, bottomMargin=%s getMarginStart=%s, getMarginEnd=%s ",
                        marginLp.topMargin, marginLp.bottomMargin, marginLp.getMarginStart(), marginLp.getMarginEnd());
            }

            childEnd = childStart + startMargin + child.getMeasuredWidth();

            childBottom = childTop + topMargin + child.getMeasuredHeight();

            Logger.d("width=%s, height=%s ", lp.width, lp.height);

            child.layout(childStart + startMargin, childTop + topMargin, childEnd, childBottom);

            childStart = childEnd + endMargin;

        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new GridLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LinearLayout.LayoutParams) {
            return new GridLayout.LayoutParams(lp);
        } else if (lp instanceof MarginLayoutParams) {
            return new GridLayout.LayoutParams((ViewGroup.MarginLayoutParams) lp);
        }
        return new GridLayout.LayoutParams(lp);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
