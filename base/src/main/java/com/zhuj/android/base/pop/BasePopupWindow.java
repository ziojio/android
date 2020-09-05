package com.zhuj.android.base.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public abstract class BasePopupWindow extends PopupWindow {
    protected int mPopupWidth;
    protected int mPopupHeight;

    protected abstract int layoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected abstract void setLayoutParams(int width, int height);

    public BasePopupWindow() {
    }

    public BasePopupWindow(Context context, int layoutId) {
        this(context, layoutId, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopupWindow(Context context, int layoutId, int width, int height) {
        this(View.inflate(context, layoutId, null), width, height);
    }

    public BasePopupWindow(View contentView) {
        this(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height, false);
        init();
        initBehavior();
    }

    /**
     * 默认可聚焦、可外部点击消失、无背景
     */
    private void init() {
        setFocusable(true);
        setOutsideTouchable(true);
        // 必须设置，否则获得焦点后页面上其他地方点击无响应
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 修改初始化的顺序，在其中添加其他操作
     */
    protected void initBehavior() {
        initView();
        initData();
        initEvent();
    }

    public final Context requireContext() {
        Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("context is NULL");
        }
        return context;
    }

    public Context getContext() {
        return getContentView().getContext();
    }

    public final View requireView() {
        View view = getContentView();
        if (view == null) {
            throw new IllegalStateException("content view is NULL");
        }
        return view;
    }

    public <T extends View> T findViewById(int resId) {
        return requireView().findViewById(resId);
    }

    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     *
     * @param view
     */
    public void showUpViewCenter(View view) {
        if (isShowing()) {
            dismiss();
            return;
        }
        // 获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        // 在控件上方显示
        showAtLocation(view, Gravity.NO_GRAVITY,
                (location[0] + view.getWidth() / 2) - mPopupWidth / 2,
                location[1] - view.getHeight() / 2 - mPopupHeight);
    }

    /**
     * 设置显示在v上方 (以v的左边距为开始位置)
     *
     * @param view
     */
    public void showUpViewStart(View view) {
        if (isShowing()) {
            dismiss();
            return;
        }
        // 获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        // 在控件上方显示
        showAtLocation(view, Gravity.NO_GRAVITY,
                (location[0]) - mPopupWidth / 2,
                location[1] - mPopupHeight);
    }

    /**
     * 点击显示或者隐藏弹窗
     *
     * @param v    点击显示弹窗的控件
     * @param xoff x轴偏移量
     * @param yoff y轴偏移量
     */
    public void showAsDropDown(View v, int xoff, int yoff) {
        if (isShowing()) {
            dismiss();
            return;
        }
        showAsDropDown(v, xoff, yoff);
    }
}
