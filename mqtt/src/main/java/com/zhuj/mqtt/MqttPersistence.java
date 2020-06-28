package com.zhuj.mqtt;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhuj.mqtt.model.Subscription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MQTT 参数本地持久化，使用SharedPreferences进行存储
 */
public final class MqttPersistence {
    private MqttPersistence() {
    }

    private static final String MQTT_NAME = "mqtt";

    private static final String KEY_CLIENT_ID = "key_client_id";
    private static final String KEY_SERVER_HOST = "key_server_host";
    private static final String KEY_SERVER_PORT = "key_server_port";
    private static final String KEY_USER_NAME = "key_user_name";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_KEEP_ALIVE = "key_keep_alive";
    private static final String KEY_TIMEOUT = "key_timeout";

    private static final String KEY_SUBSCRIPTION_TOPIC = "key_subscription_topic";

    private static SharedPreferences sharePrefs;

    private static final int DEFAULT_MQTT_PORT = 1883;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        if (sharePrefs == null) {
            sharePrefs = context.getSharedPreferences(MQTT_NAME, Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferences getSharePrefs() {
        if (sharePrefs == null) {
            throw new IllegalArgumentException("MqttPersistence is not init," +
                    "please call MqttPersistence.init(context) to init.");
        }
        return sharePrefs;
    }

    /**
     * 保存客户端ID
     *
     * @param clientId
     */
    public static void setClientId(String clientId) {
        getSharePrefs().edit().putString(KEY_CLIENT_ID, clientId).apply();
    }

    /**
     * 保存服务ip地址
     *
     * @param host
     */
    public static void setServerHost(String host) {
        getSharePrefs().edit().putString(KEY_SERVER_HOST, host).apply();
    }

    /**
     * 保存服务端口
     *
     * @param port
     */
    public static void setServerPort(int port) {
        getSharePrefs().edit().putInt(KEY_SERVER_PORT, port).apply();
    }

    /**
     * 保存用户名
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        getSharePrefs().edit().putString(KEY_USER_NAME, userName).apply();
    }

    /**
     * 保存密码
     *
     * @param password
     */
    public static void setPassword(String password) {
        getSharePrefs().edit().putString(KEY_PASSWORD, password).apply();
    }

    /**
     * 保存心跳保持的时间间隔设置
     *
     * @param keepAlive
     */
    public static void setKeepAlive(int keepAlive) {
        getSharePrefs().edit().putInt(KEY_KEEP_ALIVE, keepAlive).apply();
    }

    /**
     * 保存连接超时时间设置
     *
     * @param timeout
     */
    public static void setTimeout(int timeout) {
        getSharePrefs().edit().putInt(KEY_TIMEOUT, timeout).apply();
    }

    /**
     * 保存订阅主题的信息
     *
     * @param subscriptions
     */
    public static void setSubscriptions(List<Subscription> subscriptions) {
        getSharePrefs().edit().putStringSet(KEY_SUBSCRIPTION_TOPIC, getSubscriptionSet(subscriptions)).apply();
    }

    public static Set<String> getSubscriptionSet(List<Subscription> subscriptions) {
        Set<String> topics = new HashSet<>();
        if (subscriptions != null && !subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                topics.add(subscription.getTopic());
            }
        }
        return topics;
    }

    //==============================================//

    public static String getClientId() {
        return getSharePrefs().getString(KEY_CLIENT_ID, "");
    }

    public static String getServerHost() {
        return getSharePrefs().getString(KEY_SERVER_HOST, "");
    }

    public static int getServerPort() {
        return getSharePrefs().getInt(KEY_SERVER_PORT,  DEFAULT_MQTT_PORT);
    }

    public static String getUserName() {
        return getSharePrefs().getString(KEY_USER_NAME, "");
    }

    public static String getPassword() {
        return getSharePrefs().getString(KEY_PASSWORD, "");
    }

    public static int getKeepAlive() {
        return getSharePrefs().getInt(KEY_PASSWORD, 0);
    }

    public static int getTimeout() {
        return getSharePrefs().getInt(KEY_TIMEOUT, 0);
    }

    public static Set<String> getSubscriptionSet() {
        return getSharePrefs().getStringSet(KEY_SUBSCRIPTION_TOPIC, null);
    }

    public static List<Subscription> getSubscriptions() {
        Set<String> topics = getSharePrefs().getStringSet(KEY_SUBSCRIPTION_TOPIC, null);
        if (topics == null) {
            return null;
        }

        List<Subscription> subscriptions = new ArrayList<>();
        for (String topic : topics) {
            subscriptions.add(Subscription.wrap(topic));
        }
        return subscriptions;
    }

    /**
     * 获取mqtt连接配置信息
     *
     * @return
     */
    public static MqttOptions getMqttOptions() {
        return MqttOptions.create(getServerHost())
                .setPort(getServerPort())
                .setClientId(getClientId())
                .setUserName(getUserName())
                .setPassword(getPassword())
                .setKeepAlive(getKeepAlive())
                .setTimeout(getTimeout())
                .setSubscriptions(getSubscriptions());
    }

}
