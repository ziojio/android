package com.zhuj.android.http.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
    }

    public static byte[] toByteArray(String mBody, Charset mCharset) {
        return mBody.getBytes(mCharset);
    }

    public static void write(OutputStream writer, String mBody, Charset mCharset) {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(writer, mCharset);
        try {
            outputStreamWriter.write(mBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}