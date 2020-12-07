package com.zhuj.android.util.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.zhuj.android.util.R;


public class Toasts {
    private static Toast showingToast = null;

    private Toasts() {
    }

    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(String msg) {
        show(Androids.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    public static void showLong(String msg) {
        show(Androids.getContext(), msg, Toast.LENGTH_LONG);
    }

    public static void show(Context context, String msg, int duration) {
        if (Androids.isMainThread()) {
            Toast.makeText(context, msg, duration).show();
        } else {
            Androids.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, duration).show();
                }
            });
        }
    }

    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int bgTintColor, @ColorInt int textColor, int duration) {
        @SuppressLint("ShowToast") final Toast currentToast = Toast.makeText(context, "", duration);
        final Config config = Config.get();
        final View toastLayout = LayoutInflater.from(context).inflate(config.TOAST_LAYOUT_ID, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);

        if (bgTintColor != 0) {
            Drawable drawable = toastLayout.getBackground();
            drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        }

        if (icon == null) {
            toastIcon.setVisibility(View.GONE);
        } else {
            if (tintColor != 0) {
                icon.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            }
            toastIcon.setBackground(icon);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        if (config.typeface != null) {
            toastTextView.setTypeface(config.typeface, Typeface.NORMAL);
        }
        if (config.textSize != -1) {
            toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.textSize);
        }
        if (config.alpha != -1) {
            toastLayout.getBackground().setAlpha(config.alpha);
        }
        currentToast.setView(toastLayout);

        if (!config.allowQueue) {
            if (showingToast != null) {
                showingToast.cancel();
            }
            showingToast = currentToast;
        }
        if (config.gravity != -1) {
            currentToast.setGravity(config.gravity, config.xOffset, config.yOffset);
        }
        return currentToast;
    }

    public static class Config {
        private static volatile Config sInstance = null;

        private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        private Typeface typeface = LOADED_TOAST_TYPEFACE;

        private int TOAST_LAYOUT_ID = R.layout._toast;
        private int textSize = -1; //sp
        private boolean tintIcon = true;
        private boolean allowQueue = true;
        private int alpha = -1;
        private int gravity = -1;
        private int xOffset = 0;
        private int yOffset = 0;

        private Config() {
        }

        public static Config get() {
            if (sInstance == null) {
                sInstance = new Config();
            }
            return sInstance;
        }

        public void reset() {
            typeface = LOADED_TOAST_TYPEFACE;
            textSize = -1;
            tintIcon = true;
            allowQueue = true;
            alpha = -1;
            gravity = -1;
            xOffset = 0;
            yOffset = 0;
        }

        @CheckResult
        public Config setToastTypeface(Typeface typeface) {
            if (typeface != null) {
                this.typeface = typeface;
            }
            return this;
        }

        @CheckResult
        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }

        @CheckResult
        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public Config setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            this.alpha = alpha;
            return this;
        }

        @CheckResult
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public Config setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Config resetGravity() {
            gravity = -1;
            xOffset = 0;
            yOffset = 0;
            return this;
        }

        public Config setXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public Config setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }
    }
}
