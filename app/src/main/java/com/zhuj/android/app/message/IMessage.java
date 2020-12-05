package com.zhuj.android.app.message;


import java.util.HashMap;

public interface IMessage {

    /**
     * Message id.
     *
     * @return unique
     */
    String getMsgId();

    /**
     * Get user info of message.
     *
     * @return UserInfo of message
     */
    IUser getFromUser();

    boolean isMySend();

    /**
     * Time string that display in message list.
     *
     * @return Time string
     */
    String getTimeString();

    /**
     * Type of Message
     */
    interface MessageType {
        int TEXT = 1;
        int IMAGE = 2;
        int VOICE = 3;
        int VIDEO = 4;
        int LOCATION = 5;
        int FILE = 6;

        int EVENT = 7;

        int CUSTOM = 8;
    }

    /**
     * Type of message
     *
     * @return int
     */
    int getType();

    /**
     * Status of message, enum.
     */
    interface MessageStatus {
        int CREATED = 1;
        int SEND_GOING = 2;
        int SEND_SUCCEED = 3;
        int SEND_FAILED = 4;
        int SEND_DRAFT = 5;

        int RECEIVE_GOING = 5;
        int RECEIVE_SUCCEED = 6;
        int RECEIVE_FAILED = 7;
    }

    int getMessageStatus();


    /**
     * Text of message.
     *
     * @return text
     */
    String getText();

    /**
     * If message type is photo, voice, video or file,
     * get file path through this method.
     *
     * @return file path
     */
    String getMediaFilePath();

    /**
     * If message type is voice or video, get duration through this method.
     *
     * @return duration of audio or video, TimeUnit: SECONDS.
     */
    long getDuration();

    String getProgress();

    /**
     * Return extra key value of message
     *
     * @return {@link HashMap <>}
     */
    HashMap<String, String> getExtras();
}
