package zhuj.java.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okio.ByteString;

public class HashUtils {

    private HashUtils() {
    }

    /**
     * Returns the 128-bit MD5 hash of this byte string.
     */
    public String md5(byte[] bytes) {
        return new String(messageDigest("MD5", bytes));
    }

    /**
     * Returns the 128-bit MD5 hash of this byte string.
     */
    public String md5(String str) {
        return new String(messageDigest("MD5", str.getBytes()));
    }

    /**
     * Returns the 160-bit SHA-1 hash of this byte string.
     */
    public String sha1(byte[] bytes) {
        return ByteString.of(bytes).sha1().utf8();
    }

    /**
     * Returns the 256-bit SHA-256 hash of this byte string.
     */
    public String sha256(byte[] bytes) {
        return ByteString.of(bytes).sha256().utf8();
    }

    /**
     * Returns the 512-bit SHA-512 hash of this byte string.
     */
    public String sha512(byte[] bytes) {
        return new String(messageDigest("SHA-512", bytes));
    }

    /**
     * Returns the 512-bit SHA-512 hash of this byte string.
     */
    public String sha512(String str) {
        return new String(messageDigest("SHA-512", str.getBytes()));
    }

    private byte[] messageDigest(String algorithm, byte[] bytes) {
        try {
            return MessageDigest.getInstance(algorithm).digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

}
