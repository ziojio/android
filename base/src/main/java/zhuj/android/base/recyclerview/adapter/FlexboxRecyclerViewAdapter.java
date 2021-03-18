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
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            super.onBindViewHolder(holder, position);
        }
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


}