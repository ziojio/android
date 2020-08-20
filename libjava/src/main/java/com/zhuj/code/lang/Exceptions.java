package com.zhuj.code.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exceptions {
    private static final Logger logger = LoggerFactory.getLogger(Exceptions.class);

    public static void throwIllegalArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

    public static void throwIllegalState(String msg, Object... params) {
        throw new IllegalStateException(String.format(msg, params));
    }

    public static void handleException(Exception e, String format, Object... params) {
        if (params != null) {
            logger.error(format, params);
        }
        if (format != null) {
            logger.error(format);
        }
        logger.error(e.getMessage() + "\n -------------------------------------------------------------------------------- ");
        e.printStackTrace();
    }
}
