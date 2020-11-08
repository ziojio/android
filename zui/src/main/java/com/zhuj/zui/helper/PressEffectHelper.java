package com.zhuj.zui.helper;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class PressEffectHelper {
    public static final int NONE = 0;
    public static final int ALPHA = 1;
    public static final int BACKGROUND_COLOR = 2;  // use color as background
    public static final int BACKGROUND_TINT = 3; // Tint BlendMode
    public static final int FOREGROUND = 4;  // api 23

    private WeakReference<View> mTarget;
    private int pressEffectType;

    private ColorStateList tint;
    private ColorStateList originalTint;
    private PorterDuff.Mode tintMode;
    private PorterDuff.Mode originalTintMode;

    private ColorStateList backgroundColor;
    private Drawable originalBackground;

    private float alpha;
    private float originalAlpha;

    public PressEffectHelper(@NonNull View target) {
        mTarget = new WeakReference<>(target);
        originalAlpha = target.getAlpha();
        originalTint = target.getBackgroundTintList();
        originalTintMode = target.getBackgroundTintMode();
        originalBackground = target.getBackground();
    }

    public void setPressEffectType(int pressEffectType) {
        this.pressEffectType = pressEffectType;
    }

    public void setTint(ColorStateList tint) {
        this.tint = tint;
    }

    public void setTint(ColorStateList tint, PorterDuff.Mode tintMode) {
        this.tint = tint;
        this.tintMode = tintMode;
    }

    public void setTintColor(int tintColor) {
        this.tint = ColorStateList.valueOf(tintColor);
    }

    public void setTintColor(int tintColor, PorterDuff.Mode tintMode) {
        this.tint = ColorStateList.valueOf(tintColor);
        this.tintMode = tintMode;
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        this.tintMode = tintMode;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setBackgroundColor(ColorStateList backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = ColorStateList.valueOf(backgroundColor);
    }

    /**
     * 在 {@link View#setPressed(boolean)} 中调用，通知 helper 更新
     *
     * @param current the view to be handled, maybe not equal to target view
     * @param pressed {@link View#setPressed(boolean)} 中接收到的参数
     */
    public void onPressedChanged(View current, boolean pressed) {
        View target = mTarget.get();
        if (target == null) {
            return;
        }
        if (ALPHA == pressEffectType) {
            target.setAlpha(pressed && current.isClickable() ? alpha : originalAlpha);
        } else if (BACKGROUND_TINT == pressEffectType) {
            target.setBackgroundTintList(pressed ? tint : originalTint);
            target.setBackgroundTintMode(pressed ? tintMode : originalTintMode);
        } else if (BACKGROUND_COLOR == pressEffectType) {
            if (pressed) {
                target.setBackgroundColor(backgroundColor.getDefaultColor());
            } else {
                target.setBackground(originalBackground);
            }
        }
    }

    public static void backgroundTint(View view, ColorStateList tint, PorterDuff.Mode tintMode) {
        view.setBackgroundTintList(tint);
        view.setBackgroundTintMode(tintMode);
    }

    public static void backgroundTint(View view, int tintColor, PorterDuff.Mode tintMode) {
        view.setBackgroundTintList(ColorStateList.valueOf(tintColor));
        view.setBackgroundTintMode(tintMode);
    }

    public static void foreground(View view, int color) {
        view.setForeground(new ColorDrawable(color));
    }

    public static void foreground(View view, Drawable drawable) {
        view.setForeground(drawable);
    }


}
