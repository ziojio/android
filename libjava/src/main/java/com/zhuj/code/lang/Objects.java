package com.zhuj.code.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class Objects {
    private Objects() {
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection<?>) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        return false;
    }

    public static <T> T requireNonNull(final T object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 类型强转
     * @param object 需要强转的对象
     * @param clazz  需要强转的类型
     * @return 类型强转结果
     */
    public static <T> T cast(final Object object, Class<T> clazz) {
        return clazz != null && clazz.isInstance(object) ? (T) object : null;
    }

    /**
     * 类型强转
     *
     * @param object       需要强转的对象
     * @param defaultValue 强转的默认值
     * @param <T>
     * @return 类型强转结果
     */
    public static <T> T cast(Object object, T defaultValue) {
        if (defaultValue == null) {
            return null;
        } else if (object == null) {
            return null;
        } else {
            return defaultValue.getClass() == object.getClass() ? (T) object : defaultValue;
        }
    }
}
