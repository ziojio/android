package zhuj.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.zhuj.code.Preconditions;
import com.zhuj.code.json.GsonUtils;

import java.io.Serializable;
import java.util.Map;

public class Activitys {
    private Activitys() {
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

    public static void start(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    public static void start(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void start(Class<?> cls) {
        start(new Intent(Androids.getApp(), cls));
    }

    public static void start(Intent intent) {
        Androids.getApp().startActivity(intent);
    }

    /**
     * 外部应用的Activity
     */
    public static void openActivity(Context context, String appPackage, String activityClassName, Bundle extras) {
        Intent intent = createIntent(appPackage, activityClassName);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String appPackage, String activityClassName, Map<String, Object> extras) {
        Intent intent = createIntent(appPackage, activityClassName);
        if (extras != null) {
            putMapToIntentExtra(intent, extras);
        }
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String appPackage, String activityClassName) {
        Intent intent = createIntent(appPackage, activityClassName);
        context.startActivity(intent);
    }

    private static Intent createIntent(String appPackage, String activityClassName) {
        return new Intent().setComponent(new ComponentName(appPackage, activityClassName));
    }

}
