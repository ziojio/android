package zui.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import zui.R;
import zui.helper.AttrHelper;
import zui.helper.PressEffectHelper;

public class ZUIImageButton extends AppCompatImageButton {
    private PressEffectHelper mPressEffectHelper;

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
        int pressEffectType = ta.getInt(R.styleable.ZUIImageButton_zui_press_effect, PressEffectHelper.NONE);

        ta.recycle();

        getPressEffectHelper().setPressEffectType(pressEffectType);
        if (pressEffectType == PressEffectHelper.ALPHA) {
            getPressEffectHelper().setAlpha(AttrHelper.
                    getAttrFloatValue(getContext(), R.attr.zui_press_alpha, 0.75f));
        } else if (pressEffectType == PressEffectHelper.BACKGROUND_TINT) {
            getPressEffectHelper().setTintColor(
                    AttrHelper.getAttrColor(getContext(), R.attr.zui_press_backgroundTintColor),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }


    public PressEffectHelper getPressEffectHelper() {
        if (mPressEffectHelper == null) {
            mPressEffectHelper = new PressEffectHelper(this);
        }
        return mPressEffectHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getPressEffectHelper().onPressedChanged(this, pressed);
    }

}
