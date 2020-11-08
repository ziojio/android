package com.zhuj.zui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zhuj.zui.R;


public class ToolMenuView extends View {
    private Drawable leftDrawable;
    private Drawable rightDrawable;

    private int leftDrawableWidth;
    private int leftDrawableHeight;

    private int rightDrawableWidth;
    private int rightDrawableHeight;

    private boolean showDivider;
    private int lineWidth;
    private int lineHeight;
    private ColorStateList lineColor;

    public ToolMenuView(Context context) {
        this(context, null, 0);
    }

    public ToolMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(attrs, defStyle);
    }

    private void loadFromAttributes(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ToolMenuView, defStyle, 0);

        leftDrawable = a.getDrawable(R.styleable.ToolMenuView_leftDrawable);
        rightDrawable = a.getDrawable(R.styleable.ToolMenuView_rightDrawable);

        showDivider = a.getBoolean(R.styleable.ToolMenuView_showDivider, false);
        lineColor = a.getColorStateList(R.styleable.ToolMenuView_lineColor);
        lineWidth = a.getDimensionPixelSize(R.styleable.ToolMenuView_lineWidth, 0);
        lineHeight = a.getDimensionPixelSize(R.styleable.ToolMenuView_lineHeight, 0);

        leftDrawableWidth = a.getDimensionPixelSize(R.styleable.ToolMenuView_leftDrawableWidth, 0);
        leftDrawableHeight = a.getDimensionPixelSize(R.styleable.ToolMenuView_leftDrawableHeight, 0);
        rightDrawableWidth = a.getDimensionPixelSize(R.styleable.ToolMenuView_rightDrawableWidth, 0);
        rightDrawableHeight = a.getDimensionPixelSize(R.styleable.ToolMenuView_rightDrawableHeight, 0);
        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(leftDrawableWidth + rightDrawableWidth, widthSize);
        } else if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = leftDrawableWidth + rightDrawableWidth;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(leftDrawableHeight + rightDrawableHeight, heightSize);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = leftDrawableHeight + rightDrawableHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        //  allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int end = paddingLeft + leftDrawableWidth;
        leftDrawable.setBounds(paddingLeft, paddingTop, end, paddingTop + leftDrawableHeight);

        rightDrawable.setBounds(end, paddingTop, end + rightDrawableWidth, paddingTop + rightDrawableHeight);

        // canvas.drawLine();

    }

}
