package zhuj.android.base.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import zhuj.android.base.recyclerview.listener.OnItemClickListener;
import zhuj.android.base.recyclerview.viewholder.BaseViewHolder;


public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>> extends BaseAdapter<T, VH> {

    protected OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t, position));
            setSubViewClickable(holder.itemView, false);
        }
        onBind(holder, position, t);
    }

    public void onBind(VH holder, int position, T t){
        holder.onBind(t, position);
    }


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