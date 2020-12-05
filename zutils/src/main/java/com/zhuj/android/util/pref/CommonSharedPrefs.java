package com.zhuj.android.util.pref;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public abstract class CommonSharedPrefs {
    private SharedPreferences sharePrefs;

    public CommonSharedPrefs(Context context, String spName) {
        sharePrefs = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharePreferences() {
        return sharePrefs;
    }

    public void setValue(String key, String value) {
        sharePrefs.edit().putString(key, value).apply();
    }

    public String getValue(String key, String defValue) {
        return sharePrefs.getString(key, defValue);
    }

    public void setValue(String key, boolean value) {
        sharePrefs.edit().putBoolean(key, value).apply();
    }

    public boolean getValue(String key, boolean defValue) {
        return sharePrefs.getBoolean(key, defValue);
    }

    public void setValue(String key, int value) {
        sharePrefs.edit().putInt(key, value).apply();
    }

    public int getValue(String key, int defValue) {
        return sharePrefs.getInt(key, defValue);
    }

    public void setValue(String key, float value) {
        sharePrefs.edit().putFloat(key, value).apply();
    }

    public float getValue(String key, float defValue) {
        return sharePrefs.getFloat(key, defValue);
    }

    public void setValue(String key, long value) {
        sharePrefs.edit().putLong(key, value).apply();
    }

    public long getValue(String key, long defValue) {
        return sharePrefs.getLong(key, defValue);
    }

    public void setValues(String key, Set<String> value) {
        sharePrefs.edit().putStringSet(key, value).apply();
    }

    public Set<String> getValues(String key, Set<String> defValue) {
        return sharePrefs.getStringSet(key, defValue);
    }

    /**
     * 添加和移除sp改变的监听
     */
    public void registerSpChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharePrefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterSpChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        sharePrefs.unregisterOnSharedPreferenceChangeListener(listener);
    }
}