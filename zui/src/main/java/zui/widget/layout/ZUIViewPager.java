package zui.widget.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import zui.R;


public class ZUIViewPager extends ViewPager {

    private boolean isScrollSwitch;

    public ZUIViewPager(@NonNull Context context) {
        super(context);
    }

    public ZUIViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        final TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ZUIViewPager, 0, 0);
        isScrollSwitch = ta.getBoolean(R.styleable.ZUIViewPager_isScrollSwitch, true);

        ta.recycle();
    }

    public void setScrollSwitch(boolean scrollSwitch) {
        isScrollSwitch = scrollSwitch;
    }

    /**
     * 是否拦截事件
     * 拦截:   会走到自己的onTouchEvent方法里面来
     * 不拦截: 事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false; // 可行, 不拦截事件,
        // return true; // 不行, 孩子无法处理事件
        // return super.onInterceptTouchEvent(ev); // 不行, 会有细微移动
        if (isScrollSwitch) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * 是否消费事件
     * 消费:  事件就结束
     * 不消费: 往父控件传
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // return false; // 可行,不消费,传给父控件
        // return true; // 可行,消费,拦截事件
        // super.onTouchEvent(ev); //不行,
        // 虽然onInterceptTouchEvent中拦截了,
        // 但是如果 viewpager里面子控件不是 viewgroup, 还是会调用这个方法
        if (isScrollSwitch) {
            return super.onTouchEvent(ev);
        } else {
            return true; // 可行, 消费, 拦截事件
        }
    }

}
