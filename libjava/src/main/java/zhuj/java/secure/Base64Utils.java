package zhuj.java.secure;

import java.nio.charset.StandardCharsets;

import okio.ByteString;

public class Base64Utils {

    public static String encode(String str) {
        return ByteString.encodeUtf8(str).base64();
    }

    public static byte[] decode(String base64Str) {
        ByteString byteString = ByteString.decodeBase64(base64Str);
        return byteString != null ? byteString.toByteArray() : new byte[0];
    }
    public static String decodeString(String base64Str) {
        return new String(decode(base64Str), StandardCharsets.UTF_8);
    }

    /**
     * Returns this byte string encoded as <a href="http://www.ietf.org/rfc/rfc4648.txt">URL-safe Base64</a>.
     */
    public String encodeBase64Url(String url) {
        return ByteString.encodeUtf8(url).base64Url();
    }

}
