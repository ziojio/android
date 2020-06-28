package com.zhuj.android.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseHolderRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<T, VH> {

    public BaseHolderRecyclerViewAdapter(Context context) {
        super(context);
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
         */
        public ImageTextTextViewHolder(@NonNull View itemView, @IdRes int imageId, @IdRes int textMainId, @IdRes int textSubId) {
            super(itemView);
            ivImage = itemView.findViewById(imageId);
            tvTextMain = itemView.findViewById(textMainId);
            tvTextSub = itemView.findViewById(textSubId);
        }
    }

}