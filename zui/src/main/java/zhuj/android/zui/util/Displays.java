package zhuj.android.zui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

public class Displays {
    private Displays() {
    }

    public static DisplayMetrics getDisplayMetrics(Context context, boolean isReal) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            throw new IllegalStateException("window is null");
        }
        if (isReal) {
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        } else {
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics;
    }

    public static void logDisplayMetrics(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context, true);
        // 可用显示大小的绝对宽度（以像素为单位）。
        int widthPixels = metrics.widthPixels;
        // 可用显示大小的绝对高度（以像素为单位）。
        int heightPixels = metrics.heightPixels;
        // 屏幕密度表示为每英寸点数。
        int densityDpi = metrics.densityDpi;
        // 显示器的逻辑密度。
        float density = metrics.density;
        // 显示屏上显示的字体缩放系数。
        float scaledDensity = metrics.scaledDensity;
        Log.d("Displays", "widthPixels=" + widthPixels + ", heightPixels=" + heightPixels +
                ", densityDpi=" + densityDpi + ", density=" + density + ", scaledDensity=" + scaledDensity);
    }

    public static DisplayMetrics getResourceDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int pxToDp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getResourceDisplayMetrics(context));
    }

    public static int pxToSp(Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getResourceDisplayMetrics(context));

    }

    public static int dpToPx(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResourceDisplayMetrics(context));
    }

    public static int spToPx(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResourceDisplayMetrics(context));
    }

}
