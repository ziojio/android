package com.zhuj.android.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.zhuj.android.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Activitys {
    private Activitys() {
    }
    public static void putMapToIntentExtra(Intent intent, HashMap<String, Object> map) {
        if (map == null || map.size() == 0) return;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            } else if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (Float) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            }
        }
    }
    //
    // public static void putJsonToIntentExtra(Intent intent, String jsonStr) {
    //     JSONObject jsonObj = JsonUtils.toJSONObject(jsonStr);
    //     if (jsonObj != null) {
    //         for (Iterator<String> it = jsonObj.keys(); it.hasNext(); ) {
    //             String key = it.next();
    //             Object value = null;
    //             try {
    //                 value = jsonObj.get(key);
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //             if (value instanceof String) {
    //                 intent.putExtra(key, (String) value);
    //             } else if (value instanceof Integer) {
    //                 intent.putExtra(key, (int) value);
    //             } else if (value instanceof Long) {
    //                 intent.putExtra(key, (long) value);
    //             } else if (value instanceof Boolean) {
    //                 intent.putExtra(key, (boolean) value);
    //             } else if (value instanceof Double) {
    //                 intent.putExtra(key, (double) value);
    //             } else if (value instanceof Float) {
    //                 intent.putExtra(key, (float) value);
    //             } else {
    //                 intent.putExtra(key, jsonObj.getString(key));
    //             }
    //         }
    //         intent.putExtra("extra", jsonStr);
    //     }
    // }

    private static Intent createIntent(String appPackage, String activityClassName) {
        ComponentName component = new ComponentName(appPackage, activityClassName);
        Intent intent = new Intent();
        intent.setComponent(component);
        return intent;
    }

    public static void start(Context context, Class<?> cls, Bundle extras) {
        Intent intent = new Intent(context, cls);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String appPackage, String activityClassName, Bundle extras) {
        ComponentName component = new ComponentName(appPackage, activityClassName);
        Intent intent = new Intent();
        intent.setComponent(component);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


}
