package com.zhuj.android.app.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zhuj.android.app.R;
import com.zhuj.android.app.message.viewholder.TextViewHolder;

public class MsgAdapter extends BaseMessageAdapter<Message> {
    private LayoutInflater mLayoutInflater;

    public MsgAdapter(Context mContext) {
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public BaseMessageViewHolder<Message> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseMessageViewHolder<Message> holder;
        switch (viewType) {
            case TYPE_RECEIVE_TEXT:
                holder = new TextViewHolder<>(mLayoutInflater.inflate(R.layout.item_msg_text_recv, parent, false), false);
                break;
            case TYPE_SEND_TEXT:
                holder = new TextViewHolder<>(mLayoutInflater.inflate(R.layout.item_msg_text_send, parent, false), true);
                break;
            // case MsgItem.MSG_TYPE_RECV_VOICE:
            //     holder = new MsgViewHolder(mLayoutInflater.inflate(R.layout.item_msg_text_recv, parent, false));
            //     break;
            // case MsgItem.MSG_TYPE_SEND_VOICE:
            //     holder = new MsgViewHolder(mLayoutInflater.inflate(R.layout.item_msg_text_send, parent, false));
            //     break;
            default:
                throw new IllegalStateException("onCreateViewHolder default: no unreachable branch");
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMessageViewHolder<Message> holder, int position) {
        Message msg = messages.get(position);
        holder.mPosition = holder.getAdapterPosition();
        holder.mImageLoader = this.mImageLoader;
        holder.mMsgLongClickListener = this.mMsgLongClickListener;
        holder.mMsgClickListener = this.mMsgClickListener;
        holder.mAvatarClickListener = this.mAvatarClickListener;
        holder.mMsgStatusViewClickListener = this.mMsgStatusViewClickListener;
        holder.onBind(msg);
    }

}
