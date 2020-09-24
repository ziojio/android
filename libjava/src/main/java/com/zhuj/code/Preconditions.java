package com.zhuj.code;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public final class Preconditions {

    private Preconditions() {
        // Utility class.
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return obj.toString().isEmpty();
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        } else {
            return obj.getClass().isArray() && Array.getLength(obj) == 0;
        }
    }

    public static boolean isEmpty(Object[] array) {
        if (array == null) return true;
        return Array.getLength(array) == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null) return true;
        return map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null) return true;
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isPrimitive(Type type) {
        return !(type instanceof Class<?>) || ((Class<?>) type).isPrimitive();
    }

    public static boolean isPrimitiveOrWrapper(Type type) {
        return !(type instanceof Class<?>) || ((Class<?>) type).isPrimitive();
    }

    public static boolean isNotPrimitive(Type type) {
        return !(type instanceof Class<?>) || !((Class<?>) type).isPrimitive();
    }

    public static boolean isWrapClass(Class<?> clazz) {
        try {
            Class<?> type = (Class<?>) clazz.getField("TYPE").get(null);
            return type != null && type.isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
        return true;
    }

    public static boolean checkArgument(boolean expression) {
        return checkArgument(expression, "");
    }

    public static void checkNotPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || !((Class<?>) type).isPrimitive());
    }

    public static void checkPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || ((Class<?>) type).isPrimitive());
    }

    public static <T> T checkNotNull(T arg) {
        return checkNotNull(arg, "");
    }

    public static <T> T checkNotNull(T arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }

    public static String checkNotEmpty(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }

    public static <T extends Collection<?>> T checkNotEmpty(T collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty.");
        }
        return collection;
    }
}
