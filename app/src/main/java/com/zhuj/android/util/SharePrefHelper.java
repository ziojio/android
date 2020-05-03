package com.zhuj.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefHelper {
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences("preference", Context.MODE_PRIVATE);
    }

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String NICKNAME = "nickname";
    private static final String TELEPHONE = "telephone";
    private static final String UTYPE = "utype";
    private static final String ROLE = "role";
    private static final String TOKEN = "token";
    private static final String MD5SALT = "md5salt";

    public static void setId(long id) {
        if (null != sp) sp.edit().putLong(ID, id).apply();
    }

    public static long getId() {
        if (null != sp) return sp.getLong(ID, -1);
        return -1;
    }
    public static void setToken(String token) {
        if (null != sp) sp.edit().putString(TOKEN, token).apply();
    }

    public static String getToken() {
        if (null != sp) return sp.getString(TOKEN, null);
        return null;
    }

    public static void setTelephone(String telephone) {
        if (null != sp) sp.edit().putString(TELEPHONE, telephone).apply();
    }

    public static String getTelephone() {
        if (null != sp) return sp.getString(TELEPHONE, null);
        return null;
    }

    public static void setUType(int utype) {
        if (null != sp) sp.edit().putInt(UTYPE, utype).apply();
    }

    public static int getUType() {
        if (null != sp) return sp.getInt(UTYPE, -1);
        return -1;
    }

    private static final String SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";

    public static void setCachedKeyboardHeight(int height) {
        if (null != sp) {
            sp.edit().putInt(SOFT_KEYBOARD_HEIGHT, height).apply();
        }
    }

    public static int getCachedKeyboardHeight() {
        if (null != sp) {
            return sp.getInt(SOFT_KEYBOARD_HEIGHT, 500);
        }
        return 500;
    }
}