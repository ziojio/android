package com.zhuj.android.android;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;


public class Drawables {
    private Drawables() {
    }

    public enum Shape {RECTANGLE, CIRCLE, OVAL}

    public static Drawable getOvalDrawable(int width, int height, int color) {
        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setBounds(0, 0, width, height);
        final Paint paint = oval.getPaint();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return oval;
    }

    public static Drawable getRectDrawable(int width, int height, int color) {
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        final Paint paint = rect.getPaint();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return rect;
    }

    public static Drawable getCircleDrawable(int radius, int color) {
        ShapeDrawable circle = new ShapeDrawable(new OvalShape());
        circle.setBounds(0, 0, radius, radius);

        return circle;
    }

    /**
     * alpha 0 (completely transparent) and 255 (completely opaque).
     */
    private static int opacityToAlpha(float opacity) {
        return (int) (255 * opacity);
    }
}
