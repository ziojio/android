package com.zhuj.android.base.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow {
    private Context context;

    protected abstract int layoutId();

    protected abstract ViewGroup.LayoutParams layoutWidthHeight();

    protected abstract void initView();

    protected abstract void initData();

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        if (layoutId() != 0) {
            setContentView(createView(context, layoutId()));
        }
        if (layoutWidthHeight() != null) {
            setWidth(layoutWidthHeight().width);
            setHeight(layoutWidthHeight().height);
        }
        initBehavior();
    }

    /**
     * 测的不准
     *
     * @param view
     */
    protected void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 修改初始化的顺序，在其中添加其他操作
     */
    protected void initBehavior() {
        initView();
        initData();
    }

    protected View createView(Context context, int layoutId) {
        if (layoutId == 0) {
            return null;
        }
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public final Context requireContext() {
        if (context == null) {
            throw new IllegalStateException("context is NULL");
        }
        return context;
    }

    public final View requireView() {
        View view = getContentView();
        if (view == null) {
            throw new IllegalStateException("content view is NULL");
        }
        return view;
    }


}
