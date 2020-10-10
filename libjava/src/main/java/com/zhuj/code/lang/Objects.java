package com.zhuj.code.lang;

import com.google.gson.internal.$Gson$Preconditions;
import com.zhuj.code.Preconditions;

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
        } else {
            return false;
        }
    }

    /**
     * 类型强转
     *
     * @param object 需要强转的对象
     * @param clazz  需要强转的类型
     * @return 类型强转结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(final Object object, Class<T> clazz) {
        Preconditions.checkNotNull(clazz);
        return clazz.isInstance(object) ? (T) object : null;
    }

    /**
     * 类型强转
     * @param object   需要强转的对象
     * @param defValue 强转的默认值
     * @return 类型强转结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object, T defValue) {
        if (object == null) {
            return defValue;
        }
        try {
            return (T) object;
        } catch (Exception e) {
            return defValue;
        }
    }
}
