package com.zhuj.android.android;

import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPrefs {
    private static final String LIVE = "LIVE_APP";

    private static final String CHANNEL_ID = "channel_id";
    private static final String USER_ID = "user_id";

    private SharedPrefs() {
    }

    private static SharedPreferences sSP;

    public static void init(Context context) {
        if (sSP == null) {
            sSP = context.getSharedPreferences(LIVE, Context.MODE_PRIVATE);
        }
    }

    public static SharedPreferences getsSP() {
        if (sSP == null) {
            throw new IllegalStateException("SharePrefs is not init," +
                    "please call SharePrefs.init(context) to init.");
        }
        return sSP;
    }

    public static void setUserId(String userId) {
        getsSP().edit().putString(USER_ID, userId).apply();
    }

    public static void setChannelId(String channelId) {
        getsSP().edit().putString(CHANNEL_ID, channelId).apply();
    }

    public static String getChannelId() {
        return getsSP().getString(CHANNEL_ID, null);
    }

    public static String getUserId() {
        return getsSP().getString(USER_ID, null);
    }
}
