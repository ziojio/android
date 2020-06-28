package com.zhuj.mqtt.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Subscription implements Parcelable {

    /**
     * 主题
     */
    private String mTopic;
    /**
     * 质量
     * 0：发送一次
     * 1：保证接收
     * 2：只接收1次
     */
    private int mQos;

    protected Subscription(Parcel in) {
        mTopic = in.readString();
        mQos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTopic);
        dest.writeInt(mQos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Subscription(String topic, int qos) {
        mTopic = topic;
        mQos = qos;
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

    public static Subscription wrap(String topic) {
        return new Subscription(topic);
    }

    public Subscription(String topic) {
        mTopic = topic;
    }

    public String getTopic() {
        return mTopic;
    }

    public Subscription setTopic(String topic) {
        mTopic = topic;
        return this;
    }

    public int getQos() {
        return mQos;
    }

    public Subscription setQos(int qos) {
        mQos = qos;
        return this;
    }

    @Override
    public String toString() {
        return mTopic;
    }
}
