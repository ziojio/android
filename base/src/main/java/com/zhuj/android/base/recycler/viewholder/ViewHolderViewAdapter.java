package com.zhuj.android.base.recycler.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhuj.android.base.R;


public class ViewHolderViewAdapter<T extends ViewHolderDataAdapter> extends BaseViewHolder<T> {
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public TextView text1;
    public TextView text2;
    public TextView text3;
    public TextView text4;
    public TextView text5;

    public ViewHolderViewAdapter(@NonNull ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        image1 = itemView.findViewById(R.id.image1);
        image2 = itemView.findViewById(R.id.image2);
        image3 = itemView.findViewById(R.id.image3);
        text1 = itemView.findViewById(R.id.text1);
        text2 = itemView.findViewById(R.id.text2);
        text3 = itemView.findViewById(R.id.text3);
        text4 = itemView.findViewById(R.id.text4);
        text5 = itemView.findViewById(R.id.text5);
    }

    public void onBind(T data, int position) {
        if (text1 != null) {
            text1.setText(data.getText1());
        }
        if (text2 != null) {
            text2.setText(data.getText2());
        }
        if (text3 != null) {
            text3.setText(data.getText3());
        }
        if (text4 != null) {
            text4.setText(data.getText4());
        }
        if (text5 != null) {
            text5.setText(data.getText5());
        }
    }
}