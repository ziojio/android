package com.zhuj.code.android.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public final class ResUtil {
    private ResUtil() {
    }

    private static Context mContext;
    private static boolean isInit = false;

    private static void confirmInit() {
        if (!isInit) {
            throw new IllegalStateException("ResUtil还未初始化");
        }
    }

    public static void init(Context context) {
        mContext = context;
        isInit = true;
    }

    public static void release() {
        mContext = null;
        isInit = false;
    }

    public static Context getContext() {
        confirmInit();
        return mContext;
    }

    /**
     * 获取Color值
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        confirmInit();
        return mContext.getColor(resId);
    }

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        confirmInit();
        return mContext.getString(resId);
    }

    /**
     * 获取资源图片
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        confirmInit();
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
        confirmInit();
        return mContext.getResources().getDimension(resId);
    }

    /**
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(@DimenRes int resId) {
        confirmInit();
        return mContext.getResources().getDimensionPixelOffset(resId);
    }

}
