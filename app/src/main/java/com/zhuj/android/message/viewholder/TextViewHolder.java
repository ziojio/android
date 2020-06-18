package com.zhuj.android.message.viewholder;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuj.android.BuildConfig;
import com.zhuj.android.R;
import com.zhuj.android.message.BaseMessageViewHolder;
import com.zhuj.android.message.IMessage;

public class TextViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE> {

    private TextView mMsgTv;

    public TextViewHolder(View itemView, boolean isMySend) {
        super(itemView, isMySend);
        mMsgTv = (TextView) itemView.findViewById(R.id.item_msg_content);
    }

    @Override
    public void onBind(final MESSAGE message) {
        super.onBind(message);

        mMsgTv.setText(message.getText());

        boolean isAvatarExists = TextUtils.isEmpty(message.getFromUser().getAvatarPath());
        if (isAvatarExists && mImageLoader != null) {
            mImageLoader.loadAvatarImage(mAvatarIv, message.getFromUser().getAvatarPath());
        } else if (mImageLoader == null) {
            mAvatarIv.setVisibility(View.GONE);
        }
        if (mDisplayNameTv.getVisibility() == View.VISIBLE) {
            mDisplayNameTv.setText(message.getFromUser().getDisplayName());
        }

        mMsgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMsgClickListener != null) {
                    mMsgClickListener.onMessageClick(message);
                }
            }
        });

        mMsgTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mMsgLongClickListener != null) {
                    mMsgLongClickListener.onMessageLongClick(view, message);
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.w("MsgListAdapter", "Didn't set long click listener! Drop event.");
                    }
                }
                return true;
            }
        });

        mAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAvatarClickListener != null) {
                    mAvatarClickListener.onAvatarClick(message);
                }
            }
        });
    }

    public TextView getMsgTextView() {
        return mMsgTv;
    }

    public ImageView getAvatar() {
        return mAvatarIv;
    }

}