package com.zhuj.android.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    // no use mInflater, in sub class constructor set mInflater = null;
    protected LayoutInflater mInflater;
    // no use dataList, override getItemCount method
    protected List<T> dataList;

    public BaseRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<T> dataList) {
        this.dataList = dataList;
        super.notifyDataSetChanged();
    }

    public static class TextImageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvText;
        public ImageView ivImage;

        /**
         * no use view, set viewId = View.NO_ID == -1
         *
         * @param itemView custom view layout inflate view
         * @param textId
         * @param imageId
         */
        public TextImageViewHolder(@NonNull View itemView, @IdRes int textId, @IdRes int imageId) {
            super(itemView);
            tvText = itemView.findViewById(textId);
            ivImage = itemView.findViewById(imageId);
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }
}