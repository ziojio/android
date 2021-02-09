package zhuj.mqtt.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PublishMessage implements Parcelable {

    /**
     * 主题
     */
    private String mTopic;
    /**
     * 数据载体
     */
    private byte[] mPayload;
    /**
     * 请求质量
     */
    private int mQos;
    /**
     * 是否保持
     */
    private boolean mRetain;


    protected PublishMessage(Parcel in) {
        mTopic = in.readString();
        mPayload = in.createByteArray();
        mQos = in.readInt();
        mRetain = in.readByte() != 0;
    }

    public static final Creator<PublishMessage> CREATOR = new Creator<PublishMessage>() {
        @Override
        public PublishMessage createFromParcel(Parcel in) {
            return new PublishMessage(in);
        }

        @Override
        public PublishMessage[] newArray(int size) {
            return new PublishMessage[size];
        }
    };

    public static PublishMessage wrap(@NonNull String topic, @NonNull String message) {
        return new PublishMessage(topic, message);
    }

    public static PublishMessage wrap(@NonNull String topic, @NonNull byte[] payload) {
        return new PublishMessage(topic, payload);
    }

    public PublishMessage(@NonNull String topic, @NonNull String message) {
        mTopic = topic;
        mPayload = message.getBytes();
    }

    public PublishMessage(@NonNull String topic, @NonNull byte[] payload) {
        mTopic = topic;
        mPayload = payload;
    }

    public String getTopic() {
        return mTopic;
    }

    public PublishMessage setTopic(String topic) {
        mTopic = topic;
        return this;
    }

    public String getMessage() {
        return new String(mPayload);
    }

    public byte[] getPayload() {
        return mPayload;
    }

    public PublishMessage setMessage(@NonNull String message) {
        mPayload = message.getBytes();
        return this;
    }

    public PublishMessage setPayload(byte[] payload) {
        mPayload = payload;
        return this;
    }

    public int getQos() {
        return mQos;
    }

    public PublishMessage setQos(int qos) {
        mQos = qos;
        return this;
    }

    public boolean isRetain() {
        return mRetain;
    }

    public PublishMessage setRetain(boolean retain) {
        mRetain = retain;
        return this;
    }

    @Override
    public String toString() {
        return "[" + mTopic + "] " + getMessage();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTopic);
        dest.writeByteArray(mPayload);
        dest.writeInt(mQos);
        dest.writeByte((byte) (mRetain ? 1 : 0));
    }
}
