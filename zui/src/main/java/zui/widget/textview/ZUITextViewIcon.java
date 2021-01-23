package zui.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import zui.R;


public class ZUITextViewIcon extends AppCompatTextView {
    private int drawableSize;
    private int drawableWidth;
    private int drawableHeight;
    private int paddingStart;
    private int paddingTop;
    private int paddingEnd;
    private int paddingBottom;


    public ZUITextViewIcon(Context context) {
        this(context, null, 0);
    }

    public ZUITextViewIcon(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZUITextViewIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUITextViewIcon, 0, 0);
        setCompoundDrawablesFromAttributeSet(ta);
        ta.recycle();
        Drawable[] drawables = getCompoundDrawablesRelative();
        setCompoundDrawablesRelative(setDrawableBounds(drawables[0]), setDrawableBounds(drawables[1]),
                setDrawableBounds(drawables[2]), setDrawableBounds(drawables[3]));
    }

    private void setCompoundDrawablesFromAttributeSet(final TypedArray ta) {
        drawableSize = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawableSize, 0);
        drawableWidth = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawableWidth, 0);
        drawableHeight = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawableHeight, 0);
        paddingStart = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawablePaddingStart, 0);
        paddingTop = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawablePaddingTop, 0);
        paddingEnd = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawablePaddingEnd, 0);
        paddingBottom = ta.getDimensionPixelSize(R.styleable.ZUITextViewIcon_zui_drawablePaddingBottom, 0);

        if (drawableWidth == 0 && drawableHeight == 0 && drawableSize != 0) {
            drawableWidth = drawableSize;
            drawableHeight = drawableSize;
        }
    }

    public void setCompoundDrawableLayoutParams(int drawableWidth, int drawableHeight) {
        this.drawableWidth = drawableWidth;
        this.drawableHeight = drawableHeight;
    }

    public void setCompoundDrawablePadding(int paddingStart, int paddingTop, int paddingEnd, int paddingBottom) {
        this.paddingStart = paddingStart;
        this.paddingTop = paddingTop;
        this.paddingEnd = paddingEnd;
        this.paddingBottom = paddingBottom;
    }

    private Drawable setDrawableBounds(Drawable drawable) {
        if (drawable != null) {
            int width = drawableWidth != 0 ? drawableWidth : drawable.getIntrinsicWidth();
            int height = drawableHeight != 0 ? drawableHeight : drawable.getIntrinsicHeight();
            int x = paddingStart - paddingEnd;
            int y = paddingTop - paddingBottom;
            drawable.setBounds(x, y, width + x, height + y);
        }
        return drawable;
    }
}
