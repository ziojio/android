package com.zhuj.android.http;

import java.io.Closeable;

public class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
    }
}