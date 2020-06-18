package com.zhuj.android.message;

import java.util.HashMap;
import java.util.UUID;

public class Message implements IMessage {

    public int msgId;
    public int msgType;
    public boolean isMySend;
    public IUser fromUser;
    public String content;
    public long sendTime;
    public long recvTime;
    public int msgStatus = MessageStatus.CREATED;

    @Override
    public String getMsgId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public IUser getFromUser() {
        return fromUser;
    }

    @Override
    public boolean isMySend() {
        return isMySend;
    }

    @Override
    public String getTimeString() {
        return null;
    }

    @Override
    public int getType() {
        return msgType;
    }

    @Override
    public int getMessageStatus() {
        return msgStatus;
    }

    @Override
    public String getText() {
        return content;
    }

    @Override
    public String getMediaFilePath() {
        return null;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public String getProgress() {
        return null;
    }

    @Override
    public HashMap<String, String> getExtras() {
        return null;
    }
}
