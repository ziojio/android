package com.zhuj.android.base.recycler.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener<T> {
    void onItemClick(RecyclerView.ViewHolder vh, View v, int position, T t);
}