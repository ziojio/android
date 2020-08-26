package com.zhuj.code.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Reflections {
    private static final Logger logger = LoggerFactory.getLogger(Reflections.class);

    /**
     * getXXX ==> getDeclaredField
     * 获得某个类的所有的公共（public）的字段，包括父类中的字段。
     * <p>
     * getDeclaredXXX ==> getDeclaredField
     * 获得某个类的所有声明的字段，即包括public, private, protected, 但是不包括父类的申明字段
     */
    private Reflections() {
    }

    public static void logObjectField(Object obj, String... names) {
        Class<?> me = obj.getClass();
        if (names.length > 0) {
            for (String fieldName : names) {
                try {
                    Field field = me.getDeclaredField(fieldName);
                    logFieldInfo(field);
                } catch (NoSuchFieldException e) {
                    logger.warn("logFieldInfo: not found such field. name=" + fieldName);
                    e.printStackTrace();
                }
            }
        } else {
            Field[] fields = me.getDeclaredFields();
            for (Field field : fields) {
                logFieldInfo(field);
            }
        }

    }

    public static void logFieldInfo(Field field) {
        String className = field.getDeclaringClass().getSimpleName();
        String fieldName = field.getName();
        logger.info("{}.{}: DeclaringClass={}", className, fieldName, field.getDeclaringClass());
        logger.info("{}.{}: Name={}", className, fieldName, field.getName());
        logger.info("{}.{}: Modifiers={}", className, fieldName, field.getModifiers());
        logger.info("{}.{}: Type={}", className, fieldName, field.getType());
        logger.info("{}.{}: GenericType={}", className, fieldName, field.getGenericType());
        logger.info("{}.{}: DeclaredAnnotations={}", className, fieldName, Arrays.toString(field.getDeclaredAnnotations()));
        logger.info("{}.{}: Annotations={}", className, fieldName, Arrays.toString(field.getAnnotations()));
    }
}
