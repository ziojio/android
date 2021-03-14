package zui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SelectionView extends View {
    // 半径
    private float radius = 250;
    private int color;
    // 中心点, 默认在中心
    private float centerX;
    private float centerY;
    // 开始角度
    private int startAngle;
    // 扫过角度
    private int sweepAngle;

    public SelectionView(Context context) {
        this(context, null, 0);
    }

    public SelectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        // final TypedArray ta = context.getTheme()
        //         .obtainStyledAttributes(attrs, R.styleable.ZUISelectionView, 0, 0);
        //
        // ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.centerX = getWidth() / 2f;
        this.centerY = getHeight() / 2f;
        initArc(canvas);
    }

    /**
     * 绘制扇形图
     */
    private void initArc(Canvas canvas) {
        // 矩形区域
        RectF rect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        canvas.drawArc(rect, startAngle, sweepAngle, true, mPaint);
    }
}
