package com.zhuj.android.message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.zhuj.android.base.adapter.BaseRecyclerViewAdapter;
import com.zhuj.android.message.listener.OnAvatarClickListener;
import com.zhuj.android.message.listener.OnMessageClickListener;
import com.zhuj.android.message.listener.OnMessageLongClickListener;
import com.zhuj.android.message.listener.OnMessageStatusViewClickListener;

/**
 * 定义所有的具体消息内容
 */
public class MessageAdapter<MESSAGE extends IMessage> extends BaseRecyclerViewAdapter<MESSAGE, BaseRecyclerViewAdapter.TextImageViewHolder> {

    public static final int MSG_TYPE_RECV_TEXT = 101;
    public static final int MSG_TYPE_SEND_TEXT = 102;

    // private ImageLoader mImageLoader;
    private boolean mIsSelectedMode;
    private OnMessageClickListener<MESSAGE> mMsgClickListener;
    private OnMessageLongClickListener<MESSAGE> mMsgLongClickListener;
    private OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    private OnMessageStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;


    public MessageAdapter(Context context) {
        super(context);
    }


    @NonNull
    @Override
    public BaseRecyclerViewAdapter.TextImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TextImageViewHolder(mInflater.inflate(10, parent, false), View.NO_ID, View.NO_ID);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewAdapter.TextImageViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
