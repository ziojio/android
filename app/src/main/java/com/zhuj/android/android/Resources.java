package com.zhuj.android.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class Resources {
    private Resources() {
    }

    public static final String POINT = ".";
    public static final String R = "R";
    public static final String JOIN = "$";
    public static final String ANIM = "anim";
    public static final String ATTR = "attr";
    public static final String COLOR = "color";
    public static final String DIMEN = "dimen";
    public static final String DRAWABLE = "drawable";
    public static final String ID = "id";
    public static final String LAYOUT = "layout";
    public static final String MENU = "menu";
    public static final String RAW = "raw";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";

    /**
     * 获取自定义属性组
     *
     * @param context 上下文
     * @param name    名称
     */
    public static int[] getStyleable(Context context, String name) {
        try {
            return (int[]) Class.forName(context.getPackageName() + POINT + R + JOIN + STYLEABLE).getDeclaredField(name)
                    .get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取自定义属性
     *
     * @param context       上下文
     * @param styleableName 属性组名称
     * @param attributeName 属性名称
     */
    public static int getStyleableAttribute(Context context,
                                            String styleableName, String attributeName) {
        try {
            return (int) Class.forName(context.getPackageName() + POINT + R + JOIN + STYLEABLE)
                    .getDeclaredField(styleableName + "_" + attributeName)
                    .get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(resId);
        } else {
            return context.getResources().getColor(resId, context.getTheme());
        }
    }

    public static String getString(Context context, @StringRes int resId) {
        return context.getString(resId);
    }

    /**
     * 获取资源图片【和context有关】
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
    public static float getDimens(Context context, @DimenRes int resId) {
        return context.getResources().getDimension(resId);
    }

    /**
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(Context context, @DimenRes int resId) {
        return context.getResources().getDimensionPixelOffset(resId);
    }

}
