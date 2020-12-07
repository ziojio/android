package com.zhuj.android.app.message.listener;

import com.zhuj.android.app.message.IMessage;

/**
     * Callback will invoked when message item status view clicked
     *
     * @param <MESSAGE>
     */
    public interface OnMessageStatusViewClickListener<MESSAGE extends IMessage> {
        void onStatusViewClick(MESSAGE message);
    }
