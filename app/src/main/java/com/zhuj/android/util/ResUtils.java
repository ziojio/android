package com.zhuj.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class ResUtils {
    private ResUtils() {
    }

    private static Context mContext;

    /**
     * 获取Color值
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        return mContext.getColor(resId);
    }

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }

    /**
     * 获取资源图片
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return mContext.getDrawable(resId);
    }

    /**
     * 获取资源图片【和主体有关】
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        return context.getDrawable(resId);
    }

    /**
     * 获取svg资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getVectorDrawable(Context context, @DrawableRes int resId) {
        return context.getDrawable(resId);
    }

    /**
     * 获取dimes值，返回的是精确的值
     *
     * @param resId
     * @return
     */
    public static float getDimens(@DimenRes int resId) {
        return mContext.getResources().getDimension(resId);
    }

    /**
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return mContext.getResources().getDimensionPixelOffset(resId);
    }

    //    public static Drawable getOvalDrawable(int width, int height, int color) {
//        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
//        oval.setBounds(0, 0, width, height);
//        final Paint paint = oval.getPaint();
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(color);
//        return oval;
//    }
//
//    public static Drawable getRectDrawable(int width, int height, int color) {
//        ShapeDrawable rect = new ShapeDrawable(new RectShape());
//        final Paint paint = rect.getPaint();
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(color);
//        return rect;
//    }

//    public static Drawable getCircleDrawable(Context context, int color) {
//        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.pen_size_shape);
//        drawable.setColor(color);
//        return drawable;
//    }
    /**
     * alpha 0 (completely transparent) and 255 (completely opaque).
     */
    private static int opacityToAlpha(float opacity) {
        return (int) (255 * opacity);
    }

}
