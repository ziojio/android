package com.zhuj.code.android.util;


public class DrawableUtils {

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
