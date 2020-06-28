package com.zhuj.mqtt;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhuj.mqtt.model.Subscription;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.List;

public class MqttOptions implements Parcelable {

    public static final String KEY = "key_mqtt_option";

    private static final int DEFAULT_MQTT_PORT = 1883;

    /**
     * 客户端ID
     */
    private String mClientId;
    /**
     * 服务器IP
     */
    private String mHost;
    /**
     * 端口号
     */
    private int mPort;

    /**
     * 用户名
     */
    private String mUserName;
    /**
     * 密码
     */
    private String mPassword;
    /**
     * 连接超时时间
     */
    private int mTimeout;
    /**
     * 心跳保持的时间
     */
    private int mKeepAlive;
    /**
     * 订阅主题
     */
    private List<Subscription> mSubscriptions;

    public static MqttOptions create(String host) {
        return new MqttOptions(host);
    }

    public MqttOptions(String host) {
        mHost = host;
    }


    protected MqttOptions(Parcel in) {
        mClientId = in.readString();
        mHost = in.readString();
        mPort = in.readInt();
        mUserName = in.readString();
        mPassword = in.readString();
        mTimeout = in.readInt();
        mKeepAlive = in.readInt();
        mSubscriptions = in.createTypedArrayList(Subscription.CREATOR);
    }

    public static final Creator<MqttOptions> CREATOR = new Creator<MqttOptions>() {
        @Override
        public MqttOptions createFromParcel(Parcel in) {
            return new MqttOptions(in);
        }

        @Override
        public MqttOptions[] newArray(int size) {
            return new MqttOptions[size];
        }
    };

    public String getClientId() {
        return mClientId;
    }

    public MqttOptions setClientId(String clientId) {
        mClientId = clientId;
        return this;
    }

    public String getHost() {
        return mHost;
    }

    public MqttOptions setHost(String host) {
        mHost = host;
        return this;
    }

    public int getPort() {
        if (mPort == 0) {
            return  DEFAULT_MQTT_PORT;
        }
        return mPort;
    }

    public MqttOptions setPort(int port) {
        mPort = port;
        return this;
    }

    public String getUserName() {
        return mUserName;
    }

    public MqttOptions setUserName(String userName) {
        mUserName = userName;
        return this;
    }

    public String getPassword() {
        return mPassword;
    }

    public MqttOptions setPassword(String password) {
        mPassword = password;
        return this;
    }

    public int getTimeout() {
        if (mTimeout == 0) {
            return MqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT;
        }
        return mTimeout;
    }

    public MqttOptions setTimeout(int timeout) {
        mTimeout = timeout;
        return this;
    }

    public int getKeepAlive() {
        if (mKeepAlive == 0) {
            return MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT;
        }
        return mKeepAlive;
    }

    public MqttOptions setKeepAlive(int keepAlive) {
        mKeepAlive = keepAlive;
        return this;
    }

    public MqttOptions setSubscriptions(List<Subscription> subscriptions) {
        mSubscriptions = subscriptions;
        return this;
    }

    public List<Subscription> getSubscriptions() {
        return mSubscriptions;
    }

    @Override
    public String toString() {
        return "MqttOption{" +
                "mClientId='" + mClientId + '\'' +
                ", mHost='" + mHost + '\'' +
                ", mPort=" + mPort +
                ", mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mTimeout=" + mTimeout +
                ", mKeepAlive=" + mKeepAlive +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mClientId);
        dest.writeString(mHost);
        dest.writeInt(mPort);
        dest.writeString(mUserName);
        dest.writeString(mPassword);
        dest.writeInt(mTimeout);
        dest.writeInt(mKeepAlive);
        dest.writeTypedList(mSubscriptions);
    }
}
