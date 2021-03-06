package zhuj.android.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;

import zhuj.android.utils.log.Logger;

public class Drawables {
    private static final String TAG = Drawables.class.getSimpleName();

    private Drawables() {
    }

    public static Drawable createGradientDrawable(int startColor, int endColor) {
        GradientDrawable.Orientation o = GradientDrawable.Orientation.TOP_BOTTOM;
        int[] a = new int[]{startColor, endColor};
        GradientDrawable d = new GradientDrawable(o, a);
        return d;
    }

    public static Drawable createGradientDrawable(int startColor, int centerColor, int endColor) {
        GradientDrawable.Orientation o = GradientDrawable.Orientation.TOP_BOTTOM;
        int[] a = new int[]{startColor, centerColor, endColor};
        GradientDrawable d = new GradientDrawable(o, a);
        return d;
    }

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

    /**
     * ??????????????????????????????????????????????????????????????????synchronized
     */
    private static final Canvas CANVAS = new Canvas();

    /**
     * ?????????view??????Bitmap???
     * ????????????????????????????????? View ????????????????????????????????????????????? View ??? UI ?????????
     * ?????????https://github.com/tyrantgit/ExplosionField
     *
     * @param view  ???????????? View?????????????????? View ??????????????? Bitmap???
     * @param scale ??????????????????????????? Bitmap ?????????????????????????????? 0 ??? 1???
     */
    public static Bitmap createBitmapFromView(View view, float scale) {
        if (view instanceof ImageView) {
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        int viewHeight = 0;
        if (view instanceof ScrollView) {
            for (int i = 0; i < ((ScrollView) view).getChildCount(); i++) {
                viewHeight += ((ScrollView) view).getChildAt(i).getHeight();
            }
        } else if (view instanceof NestedScrollView) {
            for (int i = 0; i < ((NestedScrollView) view).getChildCount(); i++) {
                viewHeight += ((NestedScrollView) view).getChildAt(i).getHeight();
            }
        } else {
            viewHeight = view.getHeight();
        }

        Bitmap bitmap = createBitmapSafely((int) (view.getWidth() * scale),
                (int) (viewHeight * scale), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (CANVAS) {
                Canvas canvas = CANVAS;
                canvas.setBitmap(bitmap);
                canvas.save();
                // ?????? View ???????????????????????????????????? Bitmap ?????????????????????
                canvas.drawColor(Color.WHITE);
                canvas.scale(scale, scale);
                view.draw(canvas);
                canvas.restore();
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }


    public static Bitmap createBitmapFromWebView(WebView view) {
        return createBitmapFromWebView(view, 1f);
    }

    public static Bitmap createBitmapFromWebView(WebView view, float scale) {
        view.clearFocus();
        int viewHeight = (int) (view.getContentHeight() * view.getScale());
        Bitmap bitmap = createBitmapSafely((int) (view.getWidth() * scale), (int) (viewHeight * scale), Bitmap.Config.ARGB_8888, 1);

        int unitHeight = view.getHeight();
        int bottom = viewHeight;

        if (bitmap != null) {
            synchronized (CANVAS) {
                Canvas canvas = CANVAS;
                canvas.setBitmap(bitmap);
                // ?????? View ???????????????????????????????????? Bitmap ?????????????????????
                canvas.drawColor(Color.WHITE);
                canvas.scale(scale, scale);
                while (bottom > 0) {
                    if (bottom < unitHeight) {
                        bottom = 0;
                    } else {
                        bottom -= unitHeight;
                    }
                    canvas.save();
                    canvas.clipRect(0, bottom, canvas.getWidth(), bottom + unitHeight);
                    view.scrollTo(0, bottom);
                    view.draw(canvas);
                    canvas.restore();
                }
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }


    public static Bitmap createBitmapFromView(View view) {
        return createBitmapFromView(view, 1f);
    }

    /**
     * ?????????view??????Bitmap??????view???????????????leftCrop/topCrop/rightCrop/bottomCrop
     */
    public static Bitmap createBitmapFromView(View view, int leftCrop, int topCrop, int rightCrop, int bottomCrop) {
        Bitmap originBitmap = Drawables.createBitmapFromView(view);
        if (originBitmap == null) {
            return null;
        }
        Bitmap cutBitmap = createBitmapSafely(view.getWidth() - rightCrop - leftCrop, view.getHeight() - topCrop - bottomCrop, Bitmap.Config.ARGB_8888, 1);
        if (cutBitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(cutBitmap);
        Rect src = new Rect(leftCrop, topCrop, view.getWidth() - rightCrop, view.getHeight() - bottomCrop);
        Rect dest = new Rect(0, 0, view.getWidth() - rightCrop - leftCrop, view.getHeight() - topCrop - bottomCrop);
        canvas.drawColor(Color.WHITE); // ?????? View ???????????????????????????????????? Bitmap ?????????????????????
        canvas.drawBitmap(originBitmap, src, dest, null);
        originBitmap.recycle();
        return cutBitmap;
    }

    /**
     * ???????????????bitmap???
     * ???????????? Bitmap ???????????? OOM??????????????????????????? GC - System.gc()??????????????????????????????
     *
     * @param width      Bitmap ?????????
     * @param height     Bitmap ?????????
     * @param config     ???????????? Bitmap.Config???
     * @param retryCount ?????? Bitmap ????????? OOM ??????????????????????????????
     * @return ??????????????? Bitmap???
     */
    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        //width and height must be > 0
        if (width <= 0 || height <= 0) {
            return null;
        }
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param resources    Resources?????????????????????BitmapDrawable
     * @param width        ???????????????
     * @param height       ???????????????
     * @param cornerRadius ?????????????????????????????????0
     * @param filledColor  ??????????????????
     * @return ???????????????????????????
     */
    public static BitmapDrawable createDrawableWithSize(Resources resources, int width, int height, int cornerRadius, @ColorInt int filledColor) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        if (filledColor == 0) {
            filledColor = Color.TRANSPARENT;
        }
        if (cornerRadius > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(filledColor);
            canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);
        } else {
            canvas.drawColor(filledColor);
        }
        return new BitmapDrawable(resources, output);
    }

    /**
     * ??????Drawable?????????
     * <b>????????????Drawable??????mutate()?????????????????????????????????Drawable??????????????????????????????????????????mutate()</b>
     */
    public static ColorFilter setDrawableTintColor(Drawable drawable, @ColorInt int tintColor) {
        LightingColorFilter colorFilter = new LightingColorFilter(Color.argb(255, 0, 0, 0), tintColor);
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        return colorFilter;
    }

    /**
     * ?????????drawable??????bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        if (!(intrinsicWidth > 0 && intrinsicHeight > 0)) {
            return null;
        }

        try {
            Bitmap.Config config = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param startColor ???????????????
     * @param endColor   ???????????????
     * @param radius     ????????????
     * @param centerX    ??????????????? X ?????????
     * @param centerY    ??????????????? Y ?????????
     * @return ?????????????????????????????????
     */
    public static GradientDrawable createCircleGradientDrawable(@ColorInt int startColor,
                                                                @ColorInt int endColor, int radius,
                                                                @FloatRange(from = 0f, to = 1f) float centerX,
                                                                @FloatRange(from = 0f, to = 1f) float centerY) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{
                startColor,
                endColor
        });
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setGradientRadius(radius);
        gradientDrawable.setGradientCenter(centerX, centerY);
        return gradientDrawable;
    }


    /**
     * ?????????????????????????????????????????????Drawable???
     *
     * @param separatorColor ??????????????????
     * @param bgColor        Drawable ???????????????
     * @param top            true ??????????????????????????????false ?????????????????????
     * @return ?????????????????? Drawable???
     */
    public static LayerDrawable createItemSeparatorBg(@ColorInt int separatorColor, @ColorInt int bgColor, int separatorHeight, boolean top) {

        ShapeDrawable separator = new ShapeDrawable();
        separator.getPaint().setStyle(Paint.Style.FILL);
        separator.getPaint().setColor(separatorColor);

        ShapeDrawable bg = new ShapeDrawable();
        bg.getPaint().setStyle(Paint.Style.FILL);
        bg.getPaint().setColor(bgColor);

        Drawable[] layers = {separator, bg};
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        layerDrawable.setLayerInset(1, 0, top ? separatorHeight : 0, 0, top ? 0 : separatorHeight);
        return layerDrawable;
    }

}
