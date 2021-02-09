package zui.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import zui.R;

/**
 * 设置不滑动的最大的高度，小于最大高度时包裹内容，不滑动
 */
public class ZUIScrollView extends ScrollView {
    private int maxHeight;

    public ZUIScrollView(Context context) {
        super(context);
        loadFromAttributes(context, null);
    }

    public ZUIScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadFromAttributes(context, attrs);
    }

    public ZUIScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUIScrollView, 0, 0);
        maxHeight = ta.getDimensionPixelSize(R.styleable.ZUIScrollView_maxHeight, 0);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}