package zhuj.android.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;

import zhuj.android.utils.log.Logger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Views {
    private Views() {
    }

    public static void updateLayoutParamsHeight(View view, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void updateLayoutParamsWidth(View view, int width) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        params.width = width;
        view.setLayoutParams(params);
    }

    public static void updateLayoutParams(View view, int width, int height) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params == null) {
            return;
        }
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void updateMarginLayoutParams(View view, int start, int top, int end, int bottom) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }
        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) params;
        params1.setMargins(start, top, end, bottom);
        view.setLayoutParams(params1);
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

    public static void setSubViewClickable(View view, boolean enable) {
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
        }
    }

    public static void setPressEffectAlpha(View view, float toAlpha, int duration) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_DOWN) {
                    view.animate().alpha(toAlpha).setDuration(duration).start();
                } else if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_OUTSIDE
                        || action == MotionEvent.ACTION_CANCEL) {
                    view.animate().alpha(1f).setDuration(duration).start();
                }
                return false;
            }
        });
    }

    public static void setPressEffectForeground(View view, int frontColor) {
        view.setOnTouchListener(new View.OnTouchListener() {
            private final ColorDrawable drawable = new ColorDrawable(frontColor);

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                if (action == MotionEvent.ACTION_DOWN) {
                    view.setForeground(drawable);
                } else if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_OUTSIDE
                        || action == MotionEvent.ACTION_CANCEL) {
                    view.setForeground(null);
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
            if (drawables[0] != null) start.setBounds(drawables[0].getBounds());
            drawables[0] = start;
        }
        if (top != null) {
            if (drawables[1] != null) top.setBounds(drawables[1].getBounds());
            drawables[1] = top;
        }
        if (end != null) {
            if (drawables[2] != null) end.setBounds(drawables[2].getBounds());
            drawables[2] = end;
        }
        if (bottom != null) {
            if (drawables[3] != null) bottom.setBounds(drawables[3].getBounds());
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

    public static ViewGroup.LayoutParams measureLayout(Activity context, @LayoutRes int layoutId) {
        FrameLayout frameLayout = new FrameLayout(Androids.getApp());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return context.getLayoutInflater().inflate(layoutId, frameLayout, false).getLayoutParams();
    }

    public static ViewGroup.LayoutParams measureLayout(@LayoutRes int layoutId) {
        FrameLayout frameLayout = new FrameLayout(Androids.getApp());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return LayoutInflater.from(Androids.getApp()).inflate(layoutId, frameLayout, false).getLayoutParams();
    }

    public static ViewGroup.LayoutParams measureLayout(@LayoutRes int layoutId, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = new FrameLayout(Androids.getApp());
        frameLayout.setLayoutParams(layoutParams);
        return LayoutInflater.from(Androids.getApp()).inflate(layoutId, frameLayout, false).getLayoutParams();
    }

}
