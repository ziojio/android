package zhuj.android.base.recycler.adapter;

import android.util.SparseIntArray;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import zhuj.android.Views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import zhuj.android.base.recycler.listener.OnItemClickListener;
import zhuj.android.base.recycler.viewholder.BaseViewHolder;

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {
    protected ArrayList<T> data = new ArrayList<>();
    protected SparseIntArray layouts = new SparseIntArray();

    /**
     * @param viewType 0 代表默认的单布局
     * @param layoutId
     */
    public void addViewType(int viewType, @LayoutRes int layoutId) {
        this.layouts.put(viewType, layoutId);
    }

    protected OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t));
            Views.setSubViewClickable(holder.itemView, false);
        }
        onBind(holder, position, t);
    }

    public void onBind(VH holder, int position, T t){
        holder.onBind(t, position);
    }

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