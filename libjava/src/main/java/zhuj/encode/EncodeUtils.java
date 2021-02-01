package zhuj.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okio.ByteString;

public class EncodeUtils {


    public static String md5(String str) {
        return ByteString.encodeUtf8(str).md5().utf8();
    }

    public static String base64(String str) {
        return ByteString.encodeUtf8(str).base64();
    }

    public static String decodeBase64(String base64) {
        ByteString string = ByteString.decodeBase64(base64);
        return string != null ? string.utf8() : null;
    }

    public static String encodeUrl(String url) {
        try {
           return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
