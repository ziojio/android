package zhuj.android.base.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import zhuj.android.base.recyclerview.viewholder.DefaultViewHolder;
import zhuj.android.utils.Views;


public abstract class FlexboxRecyclerViewAdapter<T> extends DefaultRecyclerViewAdapter<T> {
    protected int column;

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        if (position >= getData().size()) {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }
        holder.itemView.setVisibility(View.VISIBLE);
        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t, position));
            Views.setSubViewClickable(holder.itemView, false);
        } else {
            holder.itemView.setOnClickListener(null);
        }
        onBind(holder, position, t);
    }

    @Override
    public int getItemCount() {
        int size = getData().size();
        if (size == 0) return 0;
        if (column == 0) return size;
        int i = size % column;
        if (i == 0) return size;
        return size + column - i;
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