package com.zhuj.android.message;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.R;

/**
 * 这个抽象类定义所有的具体消息内容
 * 初始化共有的一些布局
 */
public abstract class MessageViewHolder extends RecyclerView.ViewHolder {

    public static final int MSG_TYPE_RECV_TEXT = 101;
    public static final int MSG_TYPE_SEND_TEXT = 102;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
