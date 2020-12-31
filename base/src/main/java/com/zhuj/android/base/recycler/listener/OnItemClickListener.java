package com.zhuj.android.base.recycler.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.base.recycler.viewholder.DefaultViewHolder;

public interface OnItemClickListener<T> {
    void onItemClick(DefaultViewHolder vh, View v, int position, T t);
}