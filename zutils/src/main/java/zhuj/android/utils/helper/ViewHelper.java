package zhuj.android.utils.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

public class ViewHelper {
    protected final SparseArray<View> views = new SparseArray<>();
    protected final View rootView;

    public ViewHelper(View view) {
        this.rootView = view;
    }

    public Context getContext() {
        return rootView.getContext();
    }

    public View getRootView() {
        return rootView;
    }

    public <T extends View> T findViewById(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends View> T getView(Class<T> viewClass, @IdRes int viewId) {
        return getView(viewId);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        T view = findViewById(viewId);
        if (view == null) {
            throw new IllegalArgumentException("not find view for id#" + viewId);
        }
        return view;
    }

    public ViewHelper setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public ViewHelper setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }
    public ViewHelper setText(@IdRes int viewId, CharSequence value) {
        getView(TextView.class, viewId).setText(value);
        return this;
    }

    public ViewHelper setText(@IdRes int viewId, @StringRes int strId) {
        getView(TextView.class, viewId).setText(strId);
        return this;
    }

    public ViewHelper setTextColor(@IdRes int viewId, @ColorInt int color) {
        getView(TextView.class, viewId).setTextColor(color);
        return this;
    }

    public ViewHelper setTextColorRes(@IdRes int viewId, @ColorRes int colorRes) {
        getView(TextView.class, viewId).setTextColor(getContext().getColor(colorRes));
        return this;
    }

    public ViewHelper setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        getView(ImageView.class, viewId).setImageResource(imageResId);
        return this;
    }

    public ViewHelper setImageDrawable(@IdRes int viewId, Drawable drawable) {
        getView(ImageView.class, viewId).setImageDrawable(drawable);
        return this;
    }

    public ViewHelper setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        getView(ImageView.class, viewId).setImageBitmap(bitmap);
        return this;
    }

    public ViewHelper setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }

    public ViewHelper setBackgroundResource(@IdRes int viewId, @DrawableRes int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHelper setVisible(@IdRes int viewId, boolean isVisible) {
        getView(viewId).setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public ViewHelper setGone(@IdRes int viewId, boolean isGone) {
        getView(viewId).setVisibility(isGone ? View.GONE : View.VISIBLE);
        return this;
    }

    public ViewHelper setEnabled(@IdRes int viewId, boolean isEnabled) {
        getView(viewId).setEnabled(isEnabled);
        return this;
    }

    public ViewHelper setSelected(@IdRes int viewId, boolean isSelected) {
        getView(viewId).setSelected(isSelected);
        return this;
    }

}