package  zhuj.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Toasts {

    private Toasts() {
    }

    public static void show(String msg) {
        show(Androids.getApp(), msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        show(Androids.getApp(), msg, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    public static void show(Context context, String msg, int duration) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(() -> Toast.makeText(context, msg, duration).show());
        } else if (Androids.isMainThread()) {
            Toast.makeText(context, msg, duration).show();
        } else {
            Androids.runOnUiThread(() -> Toast.makeText(context, msg, duration).show());
        }
    }

    /**
     * 显示 Toast
     *
     * @param message  提示信息
     * @param duration 显示时间长短
     */
    public static void showCustom(Context context, String message, int duration) {
        showCustomHorizontalCenter(context, message, duration, 0.8f);
    }

    /**
     * 显示 Toast
     *
     * @param message  提示信息
     * @param duration 显示时间长短
     */
    public static void showCustomHorizontalCenter(Context context, String message, int duration, float offsetPercent) {
        int p = (int) (context.getResources().getDisplayMetrics().heightPixels * offsetPercent);
        showCustom(context, message, duration, Gravity.CENTER_HORIZONTAL, 0, p);
    }

    /**
     * 显示 Toast
     *
     * @param message  提示信息
     * @param duration 显示时间长短
     */
    public static void showCustom(Context context, String message, int duration, int gravity, int x, int y) {
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(() -> showCustomToast(context, message, duration, gravity, x, y));
        } else if (Androids.isMainThread()) {
            showCustomToast(context, message, duration, gravity, x, y);
        } else {
            Androids.runOnUiThread(() -> showCustomToast(context, message, duration, gravity, x, y));
        }
    }

    private static void showCustomToast(Context context, String message, int duration, int gravity, int x, int y) {
        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(gravity, x, y);
        toast.setView(createTextToastView(context, message));
        toast.show();
    }

    /**
     * 创建自定义 Toast View
     *
     * @param message 文本消息
     * @return View
     */
    private static View createTextToastView(Context context, String message) {
        // 画圆角矩形背景
        float rc = dp2px(context, 6);
        RoundRectShape shape = new RoundRectShape(new float[]{rc, rc, rc, rc, rc, rc, rc, rc}, null, null);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(Color.argb(225, 240, 240, 240));
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);

        // 创建View
        FrameLayout layout = new FrameLayout(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
        layout.setPadding(dp2px(context, 16), dp2px(context, 12),
                dp2px(context, 16), dp2px(context, 12));
        layout.setBackground(drawable);

        TextView textView = new TextView(context);
        textView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(15);
        textView.setText(message);
        textView.setLineSpacing(dp2px(context, 4), 1f);
        textView.setTextColor(Color.BLACK);

        layout.addView(textView);
        return layout;
    }

    private static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
