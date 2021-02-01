package zhuj.secure;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MacUtils {
    private MacUtils() {
    }

    /**
     * Returns the 160-bit SHA-1 HMAC of this byte string.
     */
    public byte[] hmacSha1(byte[] key, byte[] value) {
        return hmac("HmacSHA1", key, value);
    }

    /**
     * Returns the 256-bit SHA-256 HMAC of this byte string.
     */
    public byte[] hmacSha256(byte[] key, byte[] value) {
        return hmac("HmacSHA256", key, value);
    }

    /**
     * Returns the 512-bit SHA-512 HMAC of this byte string.
     */
    public byte[] hmacSha512(byte[] key, byte[] value) {
        return hmac("HmacSHA512", key, value);
    }

    private byte[] hmac(String algorithm, byte[] key, byte[] value) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key, algorithm));
            return mac.doFinal(value);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
