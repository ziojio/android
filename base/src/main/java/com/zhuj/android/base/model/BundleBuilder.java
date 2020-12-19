package com.zhuj.android.base.model;

import android.os.Bundle;
import android.os.Parcelable;

public class BundleBuilder {
    private Bundle bundle;

    public BundleBuilder() {
        bundle = new Bundle();
    }

    public Bundle build() {
        return bundle;
    }

    public BundleBuilder put(String key, String value) {
        bundle.putString(key, value);
        return this;
    }

    public BundleBuilder put(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public BundleBuilder put(String key, boolean value) {
        bundle.putBoolean(key, value);
        return this;
    }

    public BundleBuilder put(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public BundleBuilder put(String key, float value) {
        bundle.putFloat(key, value);
        return this;
    }

    public BundleBuilder put(String key, double value) {
        bundle.putDouble(key, value);
        return this;
    }

    public BundleBuilder put(String key, byte value) {
        bundle.putByte(key, value);
        return this;
    }

    public BundleBuilder put(String key, char value) {
        bundle.putChar(key, value);
        return this;
    }

    public BundleBuilder put(String key, Parcelable value) {
        bundle.putParcelable(key, value);
        return this;
    }

    public BundleBuilder put(String key, Bundle value) {
        bundle.putBundle(key, value);
        return this;
    }

}
