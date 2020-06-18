package com.zhuj.android.message.listener;

import android.view.View;

import com.zhuj.android.message.IMessage;

/**
     * Callback will invoked when message item is clicked
     *
     * @param <MESSAGE>
     */
    public interface OnMessageClickListener<MESSAGE extends IMessage> {
        void onMessageClick(MESSAGE message);
    }

