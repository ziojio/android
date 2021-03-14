package zhuj.android.base.recyclerview.adapter;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import zhuj.android.base.recyclerview.listener.OnItemClickListener;
import zhuj.android.base.recyclerview.viewholder.DefaultViewHolder;
import zhuj.android.utils.Views;


public abstract class DefaultRecyclerViewAdapter<T> extends BaseAdapter<T, DefaultViewHolder> {

    public DefaultRecyclerViewAdapter() {
    }

    public DefaultRecyclerViewAdapter(@LayoutRes int defLayout) {
        addViewType(0, defLayout);
    }

    protected OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaultViewHolder(parent, getLayoutRes(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        T t = data.get(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder, t, position));
            Views.setSubViewClickable(holder.itemView, false);
        } else {
            holder.itemView.setOnClickListener(null);
        }
        onBind(holder, position, t);
    }

    public abstract void onBind(DefaultViewHolder holder, int position, T data);

}