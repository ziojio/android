package zhuj.base.recycler.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import zhuj.utils.Views;

import zhuj.base.recycler.viewholder.BaseViewHolder;


public abstract class FlexboxRecyclerViewAdapter<T, VH extends BaseViewHolder<T>> extends BaseRecyclerViewAdapter<T, VH> {
    protected int column;
    protected boolean isAddEnd;

    public void setColumn(int column) {
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    protected int layoutId;

    public int getLayoutId(int viewType) {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }


    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (isAddEnd && position >= getData().size()) {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }

        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t));
            Views.setSubViewClickable(holder.itemView, false);
        }
        onBind(holder, position, t);
    }

    @Override
    public int getItemCount() {
        isAddEnd = false;
        int size = getData().size();
        if (size == 0) return 0;
        if (column == 0) return size;
        int i = size % column;
        if (i == 0) return size;
        isAddEnd = true;
        return size + column - i;
    }

}