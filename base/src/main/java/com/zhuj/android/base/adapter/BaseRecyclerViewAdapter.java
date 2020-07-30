package com.zhuj.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private LayoutInflater mInflater;
    protected List<T> dataList;

    public BaseRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<T> dataList) {
        this.dataList = dataList;
        super.notifyDataSetChanged();
    }

    public void addItem(T t) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        int idx = dataList.size();
        dataList.add(t);
        super.notifyItemInserted(idx);
    }

    public static class ImageTextTextViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvTextMain;
        public TextView tvTextSub;

        /**
         * not use view, set viewId = View.NO_ID
         *
         * @param itemView   custom view layout inflate view
         * @param imageId
         * @param textMainId
         * @param textSubId
         * @see View#NO_ID
         */
        public ImageTextTextViewHolder(@NonNull View itemView, @IdRes int imageId, @IdRes int textMainId, @IdRes int textSubId) {
            super(itemView);
            ivImage = itemView.findViewById(imageId);
            tvTextMain = itemView.findViewById(textMainId);
            tvTextSub = itemView.findViewById(textSubId);
        }
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}