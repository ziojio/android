package com.zhuj.android.app.message;

import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.app.message.listener.OnAvatarClickListener;
import com.zhuj.android.app.message.listener.OnMessageClickListener;
import com.zhuj.android.app.message.listener.OnMessageLongClickListener;
import com.zhuj.android.app.message.listener.OnMessageStatusViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义所有的具体消息内容
 */
public abstract class BaseMessageAdapter<MESSAGE extends IMessage> extends RecyclerView.Adapter<BaseMessageViewHolder<MESSAGE>> {

    // Text message
    public final int TYPE_ERROR = 0;


    public final int TYPE_RECEIVE_TEXT = 1;
    public final int TYPE_SEND_TEXT = 2;

    // Photo message
    public final int TYPE_SEND_IMAGE = 3;
    public final int TYPE_RECEIVER_IMAGE = 4;

    // Voice message
    public final int TYPE_SEND_VOICE = 6;
    public final int TYPE_RECEIVER_VOICE = 7;

    // Video message
    public final int TYPE_SEND_VIDEO = 8;
    public final int TYPE_RECEIVE_VIDEO = 9;

    // Group change message
    public final int TYPE_EVENT = 10;

    protected ImageLoader mImageLoader;
    protected boolean mIsSelectedMode;

    protected OnMessageClickListener<MESSAGE> mMsgClickListener;
    protected OnMessageLongClickListener<MESSAGE> mMsgLongClickListener;
    protected OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    protected OnMessageStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;

    protected List<MESSAGE> messages;

    public void updateDataSet(List<MESSAGE> messages) {
        this.messages = messages;
        super.notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        int type = messages.get(position).getType();
        boolean isMySend = messages.get(position).isMySend();
        switch (type) {
            case IMessage.MessageType.EVENT:
                return TYPE_EVENT;
            case IMessage.MessageType.TEXT:
                return isMySend ? TYPE_SEND_TEXT : TYPE_RECEIVE_TEXT;
            case IMessage.MessageType.IMAGE:
                return isMySend ? TYPE_SEND_IMAGE : TYPE_RECEIVER_IMAGE;
        }
        return TYPE_ERROR;
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    /**
     * Add new  message to bottom
     *
     * @param message message to be add
     */
    public void addNewMessage(MESSAGE message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * messages[0].timeString < messages[1].timeString. To load last page
     * of messages from history, use this method.
     *
     * @param messageList Last page of messages.
     */
    public void addPullRefreshData(List<MESSAGE> messageList) {
        messages.addAll(0, messageList);
        notifyItemRangeInserted(0, messageList.size());
    }

    public void setImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public void setMsgClickListener(OnMessageClickListener<MESSAGE> mMsgClickListener) {
        this.mMsgClickListener = mMsgClickListener;
    }

    public void setMsgLongClickListener(OnMessageLongClickListener<MESSAGE> mMsgLongClickListener) {
        this.mMsgLongClickListener = mMsgLongClickListener;
    }

    public void setAvatarClickListener(OnAvatarClickListener<MESSAGE> mAvatarClickListener) {
        this.mAvatarClickListener = mAvatarClickListener;
    }

    public void setMsgStatusViewClickListener(OnMessageStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener) {
        this.mMsgStatusViewClickListener = mMsgStatusViewClickListener;
    }
}
