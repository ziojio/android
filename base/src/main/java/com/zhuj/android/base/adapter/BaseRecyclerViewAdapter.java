package com.zhuj.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private LayoutInflater mInflater;
    // not use dataList, override getItemCount method
    protected List<T> dataList;

    public BaseRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<T> dataList) {
        this.dataList = dataList;
        super.notifyDataSetChanged();
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}