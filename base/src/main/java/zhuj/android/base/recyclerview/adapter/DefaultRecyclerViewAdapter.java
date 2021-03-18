package zhuj.android.base.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import zhuj.android.base.recyclerview.listener.OnItemClickListener;
import zhuj.android.base.recyclerview.viewholder.DefaultViewHolder;
import zhuj.android.utils.Views;


public abstract class DefaultRecyclerViewAdapter<T> extends BaseAdapter<T, DefaultViewHolder> {

    private OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaultViewHolder(parent, getLayoutRes(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        T t = getData().get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t, position));
            setSubViewClickable(holder.itemView, false);
        } else {
            holder.itemView.setOnClickListener(null);
        }
        onBind(holder, position, t);
    }

    public abstract void onBind(DefaultViewHolder holder, int position, T data);


    private void setSubViewClickable(View view, boolean enable) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = group.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setViewClickable(v, enable);
                } else {
                    v.setClickable(enable);
                }
            }
        }
    }

    public static void setViewClickable(View view, boolean enable) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = group.getChildAt(i);
                if (v instanceof ViewGroup) {
                    setViewClickable(v, enable);
                } else {
                    v.setClickable(enable);
                }
            }
        } else {
            view.setClickable(enable);
        }
    }
}