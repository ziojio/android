package com.zhuj.android.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsManager {

    public static SharedPreferences getAppSharedPreferences(Context context) {
        // return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
