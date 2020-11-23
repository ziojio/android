package com.zhuj.comutils.android;

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
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;

import com.zhuj.comutils.logger.Logger;

public class Drawables {
    private static final String TAG = Drawables.class.getSimpleName();

    private Drawables() {
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
     * 节省每次创建时产生的开销，但要注意多线程操作synchronized
     */
    private static final Canvas CANVAS = new Canvas();

    /**
     * 从一个view创建Bitmap。
     * 注意点：绘制之前要清掉 View 的焦点，因为焦点可能会改变一个 View 的 UI 状态。
     * 来源：https://github.com/tyrantgit/ExplosionField
     *
     * @param view  传入一个 View，会获取这个 View 的内容创建 Bitmap。
     * @param scale 缩放比例，对创建的 Bitmap 进行缩放，数值支持从 0 到 1。
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
                // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
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
                // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
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
     * 从一个view创建Bitmap。把view的区域截掉leftCrop/topCrop/rightCrop/bottomCrop
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
        canvas.drawColor(Color.WHITE); // 防止 View 上面有些区域空白导致最终 Bitmap 上有些区域变黑
        canvas.drawBitmap(originBitmap, src, dest, null);
        originBitmap.recycle();
        return cutBitmap;
    }

    /**
     * 安全的创建bitmap。
     * 如果新建 Bitmap 时产生了 OOM，可以主动进行一次 GC - System.gc()，然后再次尝试创建。
     *
     * @param width      Bitmap 宽度。
     * @param height     Bitmap 高度。
     * @param config     传入一个 Bitmap.Config。
     * @param retryCount 创建 Bitmap 时产生 OOM 后，主动重试的次数。
     * @return 返回创建的 Bitmap。
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
     * 创建一张指定大小的纯色图片，支持圆角
     *
     * @param resources    Resources对象，用于创建BitmapDrawable
     * @param width        图片的宽度
     * @param height       图片的高度
     * @param cornerRadius 图片的圆角，不需要则传0
     * @param filledColor  图片的填充色
     * @return 指定大小的纯色图片
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
     * 设置Drawable的颜色
     * <b>这里不对Drawable进行mutate()，会影响到所有用到这个Drawable的地方，如果要避免，请先自行mutate()</b>
     */
    public static ColorFilter setDrawableTintColor(Drawable drawable, @ColorInt int tintColor) {
        LightingColorFilter colorFilter = new LightingColorFilter(Color.argb(255, 0, 0, 0), tintColor);
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        return colorFilter;
    }

    /**
     * 由一个drawable生成bitmap
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
     * 创建一张渐变图片，支持韵脚。
     *
     * @param startColor 渐变开始色
     * @param endColor   渐变结束色
     * @param radius     圆角大小
     * @param centerX    渐变中心点 X 轴坐标
     * @param centerY    渐变中心点 Y 轴坐标
     * @return 返回所创建的渐变图片。
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
     * 动态创建带上分隔线或下分隔线的Drawable。
     *
     * @param separatorColor 分割线颜色。
     * @param bgColor        Drawable 的背景色。
     * @param top            true 则分割线为上分割线，false 则为下分割线。
     * @return 返回所创建的 Drawable。
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

    /////////////// VectorDrawable /////////////////////
    @Nullable
    public static Drawable getVectorDrawable(Context context, @DrawableRes int resVector) {
        try {
            return AppCompatResources.getDrawable(context, resVector);
        } catch (Exception e) {
            Logger.d(TAG, "Error in getVectorDrawable. resVector=" + resVector + ", resName=" + context.getResources().getResourceName(resVector) + e.getMessage());
            return null;
        }
    }

    public static Bitmap vectorDrawableToBitmap(Context context, @DrawableRes int resVector) {
        Drawable drawable = getVectorDrawable(context, resVector);
        if (drawable != null) {
            Bitmap b = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
            drawable.draw(c);
            return b;
        }
        return null;
    }

    /////////////// VectorDrawable /////////////////////
}