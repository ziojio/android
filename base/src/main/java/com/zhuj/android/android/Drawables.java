package com.zhuj.android.android;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

import androidx.core.graphics.drawable.DrawableCompat;

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

    public Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrap, colors);
        return wrap;
    }

    public Drawable tintDrawable(Drawable drawable, int color) {
        Drawable wrap = DrawableCompat.wrap(drawable);
        wrap.setTint(color);
        return wrap;
    }

    public void setSelectedStateListDrawable(View view, Drawable[] drawables) {
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(states[0], drawables[0]);
        stateListDrawable.addState(states[1], drawables[1]);
    }
}
