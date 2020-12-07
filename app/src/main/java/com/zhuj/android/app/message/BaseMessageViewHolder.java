package com.zhuj.android.app.message;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuj.android.app.R;
import com.zhuj.android.app.message.listener.OnAvatarClickListener;
import com.zhuj.android.app.message.listener.OnMessageClickListener;
import com.zhuj.android.app.message.listener.OnMessageLongClickListener;
import com.zhuj.android.app.message.listener.OnMessageStatusViewClickListener;

/**
 * 初始化共有的一些布局
 */
public abstract class BaseMessageViewHolder<MESSAGE extends IMessage> extends RecyclerView.ViewHolder {

    protected int mPosition;
    protected boolean mIsSelected;
    protected ImageLoader mImageLoader;
    protected boolean mIsMySend;

    protected TextView mDisplayNameTv;
    protected ImageView mAvatarIv;
    protected ImageButton mResendIb;
    protected ProgressBar mSendingPb;

    protected OnMessageLongClickListener<MESSAGE> mMsgLongClickListener;
    protected OnMessageClickListener<MESSAGE> mMsgClickListener;
    protected OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    protected OnMessageStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;

    public BaseMessageViewHolder(@NonNull View itemView, boolean isMySend) {
        super(itemView);
        this.mIsMySend = isMySend;
        mAvatarIv = (ImageView) itemView.findViewById(R.id.item_msg_user_avatar);
        mDisplayNameTv = (TextView) itemView.findViewById(R.id.item_msg_user_name);
        if (isMySend) {
            mResendIb = (ImageButton) itemView.findViewById(R.id.item_msg_error_resend);
            mSendingPb = (ProgressBar) itemView.findViewById(R.id.item_msg_sending);
        }
    }

    public void onBind(MESSAGE message) {
        if (mIsMySend) {
            switch (message.getMessageStatus()) {
                case IMessage.MessageStatus.SEND_GOING:
                    mSendingPb.setVisibility(View.VISIBLE);
                    mResendIb.setVisibility(View.GONE);
                    Log.i("TxtViewHolder", "sending message");
                    break;
                case IMessage.MessageStatus.SEND_FAILED:
                    mSendingPb.setVisibility(View.GONE);
                    Log.i("TxtViewHolder", "send message failed");
                    mResendIb.setVisibility(View.VISIBLE);
                    mResendIb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mMsgStatusViewClickListener != null) {
                                mMsgStatusViewClickListener.onStatusViewClick(message);
                            }
                        }
                    });
                    break;
                case IMessage.MessageStatus.SEND_SUCCEED:
                    mSendingPb.setVisibility(View.GONE);
                    mResendIb.setVisibility(View.GONE);
                    Log.i("TxtViewHolder", "send message succeed");
                    break;
            }
        }
    }
}
