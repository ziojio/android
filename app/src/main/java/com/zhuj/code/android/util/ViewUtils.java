package com.jbzh.android.util;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;

public class ViewUtils {

    public static ImageView caseToImageView(View view) {
        if (view instanceof ImageView)
            return (ImageView) view;
        throw new ClassCastException("view 不是 ImageView 的实例");
    }

    //(x,y)是否在view的区域内
    public static boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left && x <= right;
    }

    /**
     * 返回一个int[] 包含[ x, y ]
     */
    public static int[] onScreenLocation(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * view 在屏幕上的矩形位置
     */
    public static Rect onScreenLocationRect(View view) {
        if (view == null) {
            throw new IllegalArgumentException("view is null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return new Rect(left, top, right, bottom);
    }
}
