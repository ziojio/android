package com.zhuj.android.message.listener;

import com.zhuj.android.message.IMessage;

/**
     * Callback will invoked when message item status view clicked
     *
     * @param <MESSAGE>
     */
    public interface OnMessageStatusViewClickListener<MESSAGE extends IMessage> {
        void onStatusViewClick(MESSAGE message);
    }
