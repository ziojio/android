package com.zhuj.android.app.message.listener;

import com.zhuj.android.app.message.IMessage;

/**
 * Callback will invoked when message item is clicked
 *
 * @param <MESSAGE>
 */
public interface OnMessageClickListener<MESSAGE extends IMessage> {
    void onMessageClick(MESSAGE message);
}

