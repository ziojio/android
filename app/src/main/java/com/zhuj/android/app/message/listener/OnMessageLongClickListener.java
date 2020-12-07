package com.zhuj.android.app.message.listener;

import android.view.View;

import com.zhuj.android.app.message.IMessage;

/**
 * Callback will invoked when message item is long clicked
 *
 * @param <MESSAGE>
 */
public interface OnMessageLongClickListener<MESSAGE extends IMessage> {
    void onMessageLongClick(View view, MESSAGE message);
}
