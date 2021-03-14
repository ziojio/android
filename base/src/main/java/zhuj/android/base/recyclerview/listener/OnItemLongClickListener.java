package zhuj.android.base.recyclerview.listener;


import zhuj.android.base.recyclerview.viewholder.DefaultViewHolder;

public interface OnItemLongClickListener<T> {

    boolean onItemLongClick(DefaultViewHolder holder, T item, int position);
}
