package com.zhuj.code.android.common;

public class ByteUtils {

    public static byte[] joinBytes(byte b, byte[] bytes) {
        byte[] ret = new byte[bytes.length + 1];
        ret[0] = b;
        System.arraycopy(bytes, 0, ret,1, bytes.length);
        return ret;
    }
    public static byte[] getBytes(byte[] bytes, int offset, int length) {
        byte[] ret = new byte[bytes.length - 1];
        System.arraycopy(bytes, offset, ret,0, length);
        return ret;
    }
}
