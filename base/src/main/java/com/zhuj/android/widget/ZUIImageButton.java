package com.zhuj.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.zhuj.android.base.R;
import com.zhuj.android.widget.helper.AlphaViewHelper;

public class ZUIImageButton extends AppCompatImageButton {
    private AlphaViewHelper mAlphaViewHelper;

    public ZUIImageButton(Context context) {
        this(context, null, 0);
    }

    public ZUIImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUIImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUIImageButton, 0, 0);
        int pressEffect = ta.getInt(R.styleable.ZUIImageButton_zui_press_effect, PressEffect.NONE);
        if (pressEffect == PressEffect.ALPHA) {
            setChangeAlphaWhenPress(true);
        } else {
            setChangeAlphaWhenPress(false);
        }
        ta.recycle();
    }

    public AlphaViewHelper getAlphaViewHelper() {
        if (mAlphaViewHelper == null) {
            mAlphaViewHelper = new AlphaViewHelper(this);
        }
        return mAlphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }
}
