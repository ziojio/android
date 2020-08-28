package com.zhuj.android.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.base.adapter.BaseRecyclerViewAdapter;
import com.zhuj.android.message.listener.OnAvatarClickListener;
import com.zhuj.android.message.listener.OnMessageClickListener;
import com.zhuj.android.message.listener.OnMessageLongClickListener;
import com.zhuj.android.message.listener.OnMessageStatusViewClickListener;

/**
 * 定义所有的具体消息内容
 */
public class MessageAdapter<MESSAGE extends IMessage> extends BaseRecyclerViewAdapter<MESSAGE, MessageAdapter.TextImageViewHolder> {

    public static final int MSG_TYPE_RECV_TEXT = 101;
    public static final int MSG_TYPE_SEND_TEXT = 102;

    // private ImageLoader mImageLoader;
    private boolean mIsSelectedMode;
    private OnMessageClickListener<MESSAGE> mMsgClickListener;
    private OnMessageLongClickListener<MESSAGE> mMsgLongClickListener;
    private OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    private OnMessageStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;


    public MessageAdapter(Context context) {
        super( );
    }


    public static class TextImageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvText;
        public ImageView ivImage;

        /**
         * no use view, set viewId = View.NO_ID == -1
         *
         * @param itemView custom view layout inflate view
         * @param textId
         * @param imageId
         */
        public TextImageViewHolder(@NonNull View itemView, @IdRes int textId, @IdRes int imageId) {
            super(itemView);
            tvText = itemView.findViewById(textId);
            ivImage = itemView.findViewById(imageId);
        }
    }
    @NonNull
    @Override
    public  TextImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TextImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(10, parent, false), View.NO_ID, View.NO_ID);
    }

    @Override
    public void onBindViewHolder(@NonNull  TextImageViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
