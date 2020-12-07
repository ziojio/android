package com.zhuj.android.util.android;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IntRange;

import com.zhuj.android.util.logger.Logger;

public class Views {
    private Views() {
    }

    public static void setPressAlphaEffect(View view, float alpha, int duration) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_DOWN) {
                    view.animate().alpha(alpha).setDuration(duration).start();
                } else if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_OUTSIDE
                        || action == MotionEvent.ACTION_CANCEL) {
                    view.animate().alpha(1f).setDuration(duration).start();
                }
                return false;
            }
        });
    }

    public static void setTextViewCompoundDrawables(TextView textView, Drawable drawable, @IntRange(from = 0, to = 3) int direction) {
        if (direction == 0) {
            setTextViewCompoundDrawables(textView, drawable, null, null, null);
        } else if (direction == 1) {
            setTextViewCompoundDrawables(textView, null, drawable, null, null);
        } else if (direction == 2) {
            setTextViewCompoundDrawables(textView, null, null, drawable, null);
        } else if (direction == 3) {
            setTextViewCompoundDrawables(textView, null, null, null, drawable);
        }
    }

    public static void setTextViewCompoundDrawables(TextView textView, Drawable start, Drawable top, Drawable end, Drawable bottom) {
        Drawable[] drawables = textView.getCompoundDrawablesRelative();
        if (start != null) {
            start.setBounds(drawables[0].getBounds());
            drawables[0] = start;
        }
        if (top != null) {
            top.setBounds(drawables[1].getBounds());
            drawables[1] = top;
        }
        if (end != null) {
            end.setBounds(drawables[2].getBounds());
            drawables[2] = end;
        }
        if (bottom != null) {
            bottom.setBounds(drawables[3].getBounds());
            drawables[3] = bottom;
        }
        textView.setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public static void logMeasureSpec(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case View.MeasureSpec.UNSPECIFIED:
                Logger.d("Mode: UNSPECIFIED" + "  size: " + size + "px");
                break;
            case View.MeasureSpec.AT_MOST:
                Logger.d("Mode: AT_MOST" + "  size: " + size + "px");
                break;
            case View.MeasureSpec.EXACTLY:
                Logger.d("Mode: EXACTLY" + "  size: " + size + "px");
                break;
        }
    }
    /**
     * 设置View及其子View是否响应点击事件
     *
     * @param view
     * @param enable
     */
    public static void setViewClickable(View view, boolean enable) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = group.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setViewClickable(v, enable);
                } else {
                    v.setClickable(enable);
                }
            }
        } else {
            view.setClickable(enable);
        }
    }

    /**
     * 设置View及其子View响应指定的监听器
     *
     * @param view
     * @param clickListener
     */
    public static void setViewClickListener(View view, View.OnClickListener clickListener) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = group.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setViewClickListener(v, clickListener);
                } else {
                    v.setOnClickListener(clickListener);
                }
            }
        } else {
            view.setOnClickListener(clickListener);
        }
    }

    //(x,y)是否在view的区域内
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

    /**
     * 返回一个int[] 包含[ x, y ]
     */
    public static int[] onScreenLocation(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * view 在屏幕上的矩形位置
     */
    public static Rect onScreenLocationRect(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return new Rect(left, top, right, bottom);
    }

    /**
     * Return the width of view.
     *
     * @param view The view.
     * @return the width of view
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * Return the height of view.
     *
     * @param view The view.
     * @return the height of view
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * Measure the view.
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }
}
