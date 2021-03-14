package zhuj.android.utils;

import android.content.Intent;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import zhuj.android.Preconditions;
import zhuj.java.json.GsonUtils;

public class Intents {
    private Intents() {
    }

    public static void putMapToIntentExtra(Intent intent, Map<String, Object> map) {
        Preconditions.checkNotNull(intent);
        if (map == null || map.size() == 0) {
            return;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            } else if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            } else if (value instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) value);
            } else if (value instanceof Byte) {
                intent.putExtra(key, (byte) value);
            } else if (value instanceof Short) {
                intent.putExtra(key, (short) value);
            } else if (value instanceof Character) {
                intent.putExtra(key, (Character) value);
            } else if (value instanceof CharSequence) {
                intent.putExtra(key, (CharSequence) value);
            } else if (value instanceof Serializable) {
                intent.putExtra(key, (Serializable) value);
            } else {
                intent.putExtra(key, GsonUtils.toJson(value));
            }
        }
    }

    public static void putJsonToIntentExtra(Intent intent, String jsonStr) {
        if (intent == null || jsonStr == null || jsonStr.isEmpty()) {
            return;
        }
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObj != null && jsonObj.length() > 0) {
            for (Iterator<String> it = jsonObj.keys(); it.hasNext(); ) {
                String key = it.next();
                Object value = null;
                try {
                    value = jsonObj.get(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (value == null) {
                    continue;
                }
                if (value instanceof String) {
                    intent.putExtra(key, (String) value);
                } else if (value instanceof Integer) {
                    intent.putExtra(key, (int) value);
                } else if (value instanceof Float) {
                    intent.putExtra(key, (float) value);
                } else if (value instanceof Double) {
                    intent.putExtra(key, (double) value);
                } else if (value instanceof Boolean) {
                    intent.putExtra(key, (boolean) value);
                } else if (value instanceof Parcelable) {
                    intent.putExtra(key, (Parcelable) value);
                } else if (value instanceof Byte) {
                    intent.putExtra(key, (byte) value);
                } else if (value instanceof Short) {
                    intent.putExtra(key, (short) value);
                } else if (value instanceof Character) {
                    intent.putExtra(key, (Character) value);
                } else if (value instanceof CharSequence) {
                    intent.putExtra(key, (CharSequence) value);
                } else if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) value);
                } else {
                    intent.putExtra(key,   GsonUtils.toJson(value));
                }
            }
            intent.putExtra("extra", jsonStr);
        }
    }

}
