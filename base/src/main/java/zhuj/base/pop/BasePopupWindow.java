package zhuj.base.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jbzh.baseapp.helper.ViewHelper;

public abstract class BasePopupWindow extends PopupWindow {
    protected int mPopupWidth;
    protected int mPopupHeight;
    protected ViewHelper mViewHelper;

    public BasePopupWindow(Context context, int layoutId) {
        this(LayoutInflater.from(context).inflate(layoutId, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        mPopupWidth = width;
        mPopupHeight = height;
        init();
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

    public void setLayoutParams(int width, int height) {
        mPopupWidth = width;
        mPopupHeight = height;
        setWidth(width);
        setHeight(height);
    }

    public Context getContext() {
        return requireView().getContext();
    }

    public ViewHelper getViewHelper() {
        if (mViewHelper == null) {
            mViewHelper = new ViewHelper(requireView());
        }
        return mViewHelper;
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
                location[1] - mPopupHeight);
    }
    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     *
     * @param view
     */
    public void showDownViewCenter(View view) {
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
                location[1] + view.getHeight());
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
        super.showAsDropDown(v, xoff, yoff);
    }
}
