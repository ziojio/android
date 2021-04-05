package zui.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import zhuj.android.zui.R;
import zhuj.android.zui.util.Displays;


public class TriangleView extends View {
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;

    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 6;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private Paint mPaint;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private int mDirection;
    private Path mPath;

    public TriangleView(final Context context) {
        this(context, null, 0);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TriangleView, 0, 0);
        mColor = typedArray.getColor(R.styleable.TriangleView_trv_color, DEFAULT_COLOR);
        mDirection = typedArray.getInt(R.styleable.TriangleView_trv_direction, mDirection);
        typedArray.recycle();
        mPaint.setColor(mColor);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        mDirection = TOP;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setDirection(int mDirection) {
        this.mDirection = mDirection;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mWidth == 0 || widthMode != MeasureSpec.EXACTLY) {
            mWidth = Displays.dpToPx(getContext(), DEFAULT_WIDTH);
        }
        if (mHeight == 0 || heightMode != MeasureSpec.EXACTLY) {
            mHeight = Displays.dpToPx(getContext(), DEFAULT_HEIGHT);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mDirection) {
            case TOP:
                mPath.moveTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth / 2, 0);
                break;
            case BOTTOM:
                mPath.moveTo(0, 0);
                mPath.lineTo(mWidth / 2, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            case RIGHT:
                mPath.moveTo(0, 0);
                mPath.lineTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight / 2);
                break;
            case LEFT:
                mPath.moveTo(0, mHeight / 2);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            default:
                break;
        }

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}