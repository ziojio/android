package zui.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import zhuj.android.zui.R;


public class BorderCircleView extends View {

    private Paint circlePaint;
    private Paint borderPaint;

    private int circleColor;
    private float circleRadius;
    private int borderColor;
    private float borderWidth;
    private float space;
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
        borderColor = ta.getColor(R.styleable.BorderCircleView_borderColor, Color.BLACK);
        circleRadius = ta.getDimension(R.styleable.BorderCircleView_circleRadius, 0);
        borderWidth = ta.getDimension(R.styleable.BorderCircleView_borderWidth, 0);
        space = ta.getDimension(R.styleable.BorderCircleView_space, 0);
        showBorder = ta.getBoolean(R.styleable.BorderCircleView_showBorder, false);
        showCircle = ta.getBoolean(R.styleable.BorderCircleView_showCircle, true);
        ta.recycle();
        init();
    }

    public String getMsg() {
        return String.format("circleRadius=%s, borderWidth=%s, space=%s", circleRadius, borderWidth, space);
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        circlePaint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint();
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth); // 外边环形的宽度, 一半向内， 一半向外
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeJoin(Paint.Join.ROUND);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float w = getWidth() / 2f;
        float h = getHeight() / 2f;
        // 绘制圆
        if (showCircle && circleRadius > 0) {
            canvas.drawCircle(w, h, circleRadius, circlePaint);
        }
        // 绘制外边框
        if (showBorder && borderWidth > 0) {
            canvas.drawCircle(w, h, circleRadius + space + borderWidth / 2, borderPaint);
        }
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
                int value = (int) (borderWidth + space + circleRadius) * 2;
                if (attr == 0) {
                    newSize = getPaddingStart() + value + getPaddingEnd();
                } else if (attr == 1) {
                    newSize = getPaddingTop() + value + getPaddingBottom();
                }
                break;
        }
        return newSize;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        circlePaint.setColor(circleColor);
        invalidate();
    }

    public void setBorderWidth(int outBorderWidth) {
        this.borderWidth = outBorderWidth;
        borderPaint.setStrokeWidth(outBorderWidth);
        invalidate();
    }

    public void showBorder(boolean showBorder) {
        this.showBorder = showBorder;
        invalidate();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public int getBorderWidth() {
        return (int) borderWidth;
    }

}
