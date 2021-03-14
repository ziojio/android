package zhuj.android.base.recyclerview.listener;


import zhuj.android.base.recyclerview.viewholder.DefaultViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(DefaultViewHolder vh, T t, int position);
}