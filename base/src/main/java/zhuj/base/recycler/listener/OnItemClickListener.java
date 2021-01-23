package zhuj.base.recycler.listener;


import zhuj.base.recycler.viewholder.DefaultViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(DefaultViewHolder vh, T t);
}