package com.zhuj.android.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> dataList;

    public BaseRecyclerViewAdapter() {
        dataList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
        super.notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void addItem(T t) {
        if (t == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        int idx = dataList.size();
        dataList.add(t);
        super.notifyItemInserted(idx);
    }

    public void addAll(List<T> t) {
        if (t == null || t.size() == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        int idx = dataList.size();
        this.dataList.addAll(t);
        super.notifyItemRangeInserted(idx, t.size());
    }

    public void addAll(T[] t) {
        if (t == null || t.length == 0) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        int idx = dataList.size();
        Collections.addAll(dataList, t);
        super.notifyItemRangeInserted(idx, t.length);
    }

    public static abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull ViewGroup parent, int layoutId) {
            super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        }

        public abstract void setData(T t);
    }

    public static class ImageTextTextViewHolder<T> extends BaseViewHolder<T> {
        public ImageView ivImage;
        public TextView tvTextMain;
        public TextView tvTextSub;

        /**
         * not use view, set viewId = View.NO_ID
         *
         * @param parent
         * @param imageId
         * @param textMainId
         * @param textSubId
         * @see View#NO_ID
         */
        public ImageTextTextViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutId, @IdRes int imageId, @IdRes int textMainId, @IdRes int textSubId) {
            super(parent, layoutId);
            ivImage = itemView.findViewById(imageId);
            tvTextMain = itemView.findViewById(textMainId);
            tvTextSub = itemView.findViewById(textSubId);
        }

        @Override
        public void setData(T t) {

        }
    }

}