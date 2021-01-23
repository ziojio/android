package zhuj.base.recycler.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

public class DefaultViewHolder extends RecyclerView.ViewHolder {
    protected final SparseArray<View> views = new SparseArray<>();

    public DefaultViewHolder(@NonNull ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    public DefaultViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public <T extends View> T getView(Class<T> viewClass, @IdRes int viewId) {
        return getView(viewId);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            if (view == null) {
                throw new IllegalArgumentException("not find id#" + viewId + " in the itemView");
            }
            views.put(viewId, view);
        }
        return (T) view;
    }

    public DefaultViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        getView(View.class, viewId).setOnClickListener(listener);
        return this;
    }

    public DefaultViewHolder setText(@IdRes int viewId, CharSequence value) {
        getView(TextView.class, viewId).setText(value);
        return this;
    }

    public DefaultViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        getView(TextView.class, viewId).setText(strId);
        return this;
    }

    public DefaultViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        getView(TextView.class, viewId).setTextColor(color);
        return this;
    }

    public DefaultViewHolder setTextColorRes(@IdRes int viewId, @ColorRes int colorRes) {
        getView(TextView.class, viewId).setTextColor(itemView.getResources().getColor(colorRes));
        return this;
    }

    public DefaultViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        getView(ImageView.class, viewId).setImageResource(imageResId);
        return this;
    }

    public DefaultViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        getView(ImageView.class, viewId).setImageDrawable(drawable);
        return this;
    }

    public DefaultViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        getView(ImageView.class, viewId).setImageBitmap(bitmap);
        return this;
    }

    public DefaultViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        getView(View.class, viewId).setBackgroundColor(color);
        return this;
    }

    public DefaultViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int backgroundRes) {
        getView(View.class, viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    public DefaultViewHolder setVisible(@IdRes int viewId, boolean isVisible) {
        getView(View.class, viewId).setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public DefaultViewHolder setGone(@IdRes int viewId, boolean isGone) {
        getView(View.class, viewId).setVisibility(isGone ? View.GONE : View.VISIBLE);
        return this;
    }

    public DefaultViewHolder setEnabled(@IdRes int viewId, boolean isEnabled) {
        getView(View.class, viewId).setEnabled(isEnabled);
        return this;
    }

    public DefaultViewHolder setSelected(@IdRes int viewId, boolean isSelected) {
        getView(View.class, viewId).setSelected(isSelected);
        return this;
    }

}