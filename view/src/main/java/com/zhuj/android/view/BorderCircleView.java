package com.zhuj.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class BorderCircleView extends View {

    private Paint mPaint;
    private Paint backPaint;
    private Paint borderPaint;
    private Paint outBorderPaint;
    private int circleColor;
    private int circleRadius;
    private int outBorderColor;
    private int outBorderWidth;
    private int space;
    private boolean showCircle;
    private boolean showBorder;

    public BorderCircleView(Context context) {
        this(context, null, 0);
    }

    public BorderCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderCircleView);

        circleColor = ta.getColor(R.styleable.BorderCircleView_circleColor, Color.BLACK);
        outBorderColor = ta.getColor(R.styleable.BorderCircleView_borderColor, Color.WHITE);
        circleRadius = ta.getInteger(R.styleable.BorderCircleView_circleRadius, 5);
        outBorderWidth = dp2px(context, ta.getInteger(R.styleable.BorderCircleView_borderWidth, 0));
        space = dp2px(context, ta.getInteger(R.styleable.BorderCircleView_space, 0));
        showBorder = ta.getBoolean(R.styleable.BorderCircleView_showBorder, false);
        showCircle = ta.getBoolean(R.styleable.BorderCircleView_showCircle, false);
        ta.recycle();

        init();
    }

    public int dp2px(Context context, float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init() {

        borderPaint = new Paint();
        borderPaint.setColor(circleColor);
        borderPaint.setStrokeWidth(5);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStyle(Paint.Style.STROKE);

        outBorderPaint = new Paint();
        outBorderPaint.setColor(outBorderColor);
        outBorderPaint.setStrokeWidth(3.5f);
        outBorderPaint.setAntiAlias(true);
        outBorderPaint.setStrokeJoin(Paint.Join.ROUND);
        outBorderPaint.setStyle(Paint.Style.STROKE);

        backPaint = new Paint();
        backPaint.setColor(Color.WHITE);
        backPaint.setAntiAlias(true);
        backPaint.setStrokeJoin(Paint.Join.ROUND);
        backPaint.setStyle(Paint.Style.FILL);

        mPaint = new Paint();
        mPaint.setColor(circleColor);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.FILL);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, backPaint);
        //绘制内边框
        if (showBorder) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, outBorderWidth / 2.5f + 10, borderPaint);
        }
        //绘制外边框
        if (showBorder) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 2f, outBorderPaint);
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, outBorderWidth / 2.5f, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = onMeasureR(0, widthMeasureSpec);
        int height = onMeasureR(1, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 计算控件宽高
     */
    public int onMeasureR(int attr, int oldMeasure) {

        int newSize = 0;
        int mode = MeasureSpec.getMode(oldMeasure);
        int oldSize = MeasureSpec.getSize(oldMeasure);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                newSize = oldSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                float value;
                if (attr == 0) {
                    if (showBorder) {
                        value = (outBorderWidth / 2.5f + 40) * 2;
                    } else {
                        value = (outBorderWidth / 2.5f + 25) * 2;
                    }
                    newSize = (int) (getPaddingLeft() + value + getPaddingRight());
                } else if (attr == 1) {
                    if (showBorder) {
                        value = (outBorderWidth / 2.5f + 40) * 2;
                    } else {
                        value = (outBorderWidth / 2.5f + 25) * 2;
                    }
//                    value = (circleRadius / 2.5f + 20) * 2;
                    // 控件的高度  + getPaddingTop() +  getPaddingBottom()
                    newSize = (int) (getPaddingTop() + value + getPaddingBottom());

                }
                break;
            default:
                break;
        }

        return newSize;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        mPaint.setColor(circleColor);
        invalidate();
    }

    public void setOutBorderWidth(int level) {
        this.outBorderWidth = level;
        // this.circleRadius = dip2px(getContext(), PaintSettingWindow.PEN_SIZES[level]);
        invalidate();
    }

    public void showBorder(boolean showBorder) {
        this.showBorder = showBorder;
        invalidate();
    }

    public void setOutBorderColor(int outBorderColor) {
        this.outBorderColor = outBorderColor;
        outBorderPaint.setColor(outBorderColor);
        invalidate();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public int getOutBorderWidth() {
        return outBorderWidth;
    }
}
