package com.zhuj.android.base.recycler.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener2<T, VH extends RecyclerView.ViewHolder> {
    void onItemClick(VH vh, View v, int position, T t);
}