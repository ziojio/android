package com.zhuj.android.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Reflections {
    private static final String TAG = "Reflections";

    private Reflections() {
    }

    public static void logObjectField(Object obj, String... name) {
        Class<?> me = obj.getClass();
        if (name.length > 0) {
            for (String fieldName : name) {
                try {
                    Field field = me.getField(fieldName);
                    logField(field);
                } catch (NoSuchFieldException e) {
                    Log.w(TAG, "logFieldInfo: not found such field. name=" + fieldName);
                    e.printStackTrace();
                }
            }
        } else {
            Field[] fields = me.getFields();
            for (Field field : fields) {
                logField(field);
            }
        }

    }

    public static void logField(Field field) {
        String className = field.getDeclaringClass().getSimpleName();
        String name = field.getName();
        Log.e(className, name + " getDeclaringClass      =" + field.getDeclaringClass());
        Log.e(className, name + " getName                =" + field.getName());
        Log.e(className, name + " getModifiers           =" + field.getModifiers());
        Log.e(className, name + " getType                =" + field.getType());
        Log.e(className, name + " getGenericType         =" + field.getGenericType());
        Log.e(className, name + " getDeclaredAnnotations =" + Arrays.toString(field.getDeclaredAnnotations()));
        Log.e(className, name + " getAnnotations         =" + Arrays.toString(field.getAnnotations()));

    }
}
