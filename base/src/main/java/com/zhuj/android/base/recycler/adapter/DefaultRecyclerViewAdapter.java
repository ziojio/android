package com.zhuj.android.base.recycler.adapter;


import android.util.SparseIntArray;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.base.recycler.listener.OnItemClickListener2;
import com.zhuj.android.base.recycler.viewholder.DefaultViewHolder;
import com.zhuj.android.util.Views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DefaultRecyclerViewAdapter<T> extends RecyclerView.Adapter<DefaultViewHolder> {
    protected ArrayList<T> data = new ArrayList<>();
    protected SparseIntArray layouts = new SparseIntArray();

    public DefaultRecyclerViewAdapter() {
    }

    public DefaultRecyclerViewAdapter(@LayoutRes int defLayout) {
        addViewType(0, defLayout);
    }

    /**
     * @param viewType 0 代表默认的单布局
     * @param layoutId
     */
    public void addViewType(int viewType, @LayoutRes int layoutId) {
        this.layouts.put(viewType, layoutId);
    }

    protected OnItemClickListener2<T, DefaultViewHolder> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener2<T, DefaultViewHolder> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaultViewHolder(parent, this.layouts.get(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, v, position, t));
            Views.setSubViewClickable(holder.itemView, false);
        }
        onBind(holder, position, t);
    }

    public abstract void onBind(DefaultViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(Collection<T> dataList) {
        this.data.clear();
        if (dataList != null) this.data.addAll(dataList);
        super.notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void addItem(T item) {
        if (item == null) return;
        int idx = data.size();
        data.add(item);
        super.notifyItemInserted(idx);
    }

    public void addAll(Collection<T> items) {
        if (items == null || items.isEmpty()) return;
        int idx = data.size();
        this.data.addAll(items);
        super.notifyItemRangeInserted(idx, items.size());
    }
}