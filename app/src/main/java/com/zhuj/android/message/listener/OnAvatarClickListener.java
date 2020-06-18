package com.zhuj.android.message.listener;

import com.zhuj.android.message.IMessage;

/**
 * Callback will invoked when message item user avatar clicked
 *
 * @param <MESSAGE>
 */
public interface OnAvatarClickListener<MESSAGE extends IMessage> {
    void onAvatarClick(MESSAGE message);
}
