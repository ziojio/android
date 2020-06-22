package com.zhuj.android.model;


public class KeyValue<Key, Value> {
    protected Key key;
    protected Value value;

    public KeyValue(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

}
