package com.zhuj.code;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;


public final class Preconditions {

    private Preconditions() {
        // Utility class.
    }

    public static void checkArgument(boolean expression, @NonNull String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkArgument(boolean expression) {
        checkArgument(expression, "");
    }

    public static void checkNotPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || !((Class<?>) type).isPrimitive());
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T arg) {
        return checkNotNull(arg, "");
    }

    @NonNull
    public static <T> T checkNotNull(@Nullable T arg, @NonNull String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    @NonNull
    public static String checkNotEmpty(@Nullable String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }

    @NonNull
    public static <T extends Collection<?>> T checkNotEmpty(T collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty.");
        }
        return collection;
    }
}
