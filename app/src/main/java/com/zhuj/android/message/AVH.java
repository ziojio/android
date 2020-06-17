package com.zhuj.android.message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zhuj.android.base.adapter.BaseRecyclerViewAdapter;
import com.zhuj.android.model.UpdateAppInfo;

class AVH extends BaseRecyclerViewAdapter<UpdateAppInfo, BaseRecyclerViewAdapter.TextImageViewHolder> {

    public AVH(Context context) {
        super(context);
    }


    @NonNull
    @Override
    public BaseRecyclerViewAdapter.TextImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextImageViewHolder(mInflater.inflate(10, parent, false), View.NO_ID, View.NO_ID);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.TextImageViewHolder holder, int position) {

    }
}
