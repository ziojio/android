package com.zhuj.android.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class Activitys {
    private Activitys() {
    }

    public static void openActivity(Context context, String appPackage, String activityClassName, Bundle extras) {
        ComponentName component = new ComponentName(appPackage, activityClassName);
        Intent intent = new Intent();
        intent.setComponent(component);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


}
