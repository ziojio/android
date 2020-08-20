package com.zhuj.android.model;

import java.util.HashMap;
import java.util.Map;

public class KeyValue<Key, Value> {
    protected Key key;
    protected Value value;

    public KeyValue(Key key, Value value) {
        this.key = key;
        this.value = value;
        Map<String, Object> map = new HashMap<>();
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
