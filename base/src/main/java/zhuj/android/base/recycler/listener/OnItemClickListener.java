package zhuj.android.base.recycler.listener;


import zhuj.android.base.recycler.viewholder.DefaultViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(DefaultViewHolder vh, T t);
}