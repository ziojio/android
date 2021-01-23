package zhuj.http;


import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import zhuj.http.util.ListMap;
import zhuj.http.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.HttpCookie;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * <p>描述：头部参数</p>
 *
 * @author xuexiang
 * @since 2018/6/20 上午1:14
 */
public class HttpHeaders extends ListMap<String, String> implements Serializable {
    private static final String TAG = HttpHeaders.class.getSimpleName();

    public static final String FORMAT_HTTP_DATA = "EEE, dd MMM y HH:mm:ss 'GMT'";
    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public static final String VALUE_ACCEPT_ALL = "*/*";
    public static final String KEY_RESPONSE_CODE = "ResponseCode";
    public static final String KEY_RESPONSE_MESSAGE = "ResponseMessage";
    public static final String KEY_ACCEPT = "Accept";
    public static final String KEY_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String VALUE_ACCEPT_ENCODING = "gzip, deflate";
    public static final String KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String KEY_CONTENT_TYPE = "Content-Type";
    public static final String KEY_CONTENT_LENGTH = "Content-Length";
    public static final String KEY_CONTENT_ENCODING = "Content-Encoding";
    public static final String KEY_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String KEY_CONTENT_RANGE = "Content-Range";
    public static final String KEY_CACHE_CONTROL = "Cache-Control";
    public static final String KEY_CONNECTION = "Connection";
    public static final String KEY_DATE = "Date";
    public static final String KEY_EXPIRES = "Expires";
    public static final String KEY_E_TAG = "ETag";
    public static final String KEY_PRAGMA = "Pragma";
    public static final String KEY_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String KEY_IF_NONE_MATCH = "If-None-Match";
    public static final String KEY_LAST_MODIFIED = "Last-Modified";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_USER_AGENT = "User-Agent";
    public static final String KEY_COOKIE = "Cookie";
    public static final String KEY_COOKIE2 = "Cookie2";
    public static final String KEY_SET_COOKIE = "Set-Cookie";
    public static final String KEY_SET_COOKIE2 = "Set-Cookie2";
    public static final String KEY_ACCEPT_RANGE = "Accept-Range";
    public static final String VALUE_APPLICATION_URLENCODED = "application/x-www-form-urlencoded";
    public static final String VALUE_APPLICATION_FORM = "multipart/form-data";
    public static final String VALUE_APPLICATION_STREAM = "application/octet-stream";
    public static final String VALUE_APPLICATION_JSON = "application/json";
    public static final String VALUE_APPLICATION_XML = "application/xml";
    public static final String VALUE_KEEP_ALIVE = "keep-alive";
    public static final String VALUE_CLOSE = "close";
    public static final String KEY_HOST = "Host";
    public static final String KEY_RANGE = "Range";

    private static String acceptLanguage;
    private static String userAgent;

    public HttpHeaders() {
        super(new TreeMap<>(String::compareTo));
    }

    @Override
    public void add(String key, String value) {
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value))
            super.add(formatKey(key), value);
    }

    @Override
    public void set(String key, String value) {
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value))
            super.set(formatKey(key), value);
    }

    @Override
    public void add(String key, List<String> values) {
        if (!StringUtils.isEmpty(key) && !values.isEmpty())
            super.add(formatKey(key), values);
    }

    @Override
    public void set(String key, List<String> values) {
        if (!StringUtils.isEmpty(key) && !values.isEmpty())
            super.set(formatKey(key), values);
    }

    public void add(HttpHeaders headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            for (String value : values) {
                add(key, value);
            }
        }
    }

    @Override
    public List<String> remove(String key) {
        return super.remove(formatKey(key));
    }

    @Override
    public List<String> get(String key) {
        return super.get(formatKey(key));
    }

    @Override
    public String getFirst(String key) {
        return super.getFirst(formatKey(key));
    }

    @Override
    public boolean containsKey(String key) {
        return super.containsKey(formatKey(key));
    }

    /**
     * Replace all.
     */
    public void set(HttpHeaders headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }


    /**
     * A value of the header information.
     *
     * @param content      like {@code text/html;charset=utf-8}.
     * @param key          like {@code charset}.
     * @param defaultValue list {@code utf-8}.
     * @return If you have a value key, you will return the parsed value if you don't return the default value.
     */
    public static String parseSubValue(String content, String key, String defaultValue) {
        if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(key)) {
            StringTokenizer stringTokenizer = new StringTokenizer(content, ";");
            while (stringTokenizer.hasMoreElements()) {
                String valuePair = stringTokenizer.nextToken();
                int index = valuePair.indexOf('=');
                if (index > 0) {
                    String name = valuePair.substring(0, index).trim();
                    if (key.equalsIgnoreCase(name)) {
                        defaultValue = valuePair.substring(index + 1).trim();
                        break;
                    }
                }
            }
        }
        return defaultValue;
    }

    /**
     * All the cookies in header information.
     */
    public static List<HttpCookie> getHttpCookieList(HttpHeaders headers) {
        List<HttpCookie> cookies = new ArrayList<>();
        for (String key : headers.keySet()) {
            if (key.equalsIgnoreCase(KEY_SET_COOKIE)) {
                List<String> cookieValues = headers.get(key);
                for (String cookieStr : cookieValues) {
                    cookies.addAll(HttpCookie.parse(cookieStr));
                }
            }
        }
        return cookies;
    }

    /**
     * Format to Hump-shaped words.
     */
    public static String formatKey(String key) {
        if (StringUtils.isEmpty(key)) return null;

        key = key.toLowerCase(Locale.ENGLISH);
        String[] words = key.split("-");

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            String first = word.substring(0, 1);
            String end = word.substring(1);
            builder.append(first.toUpperCase(Locale.ENGLISH)).append(end).append("-");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.lastIndexOf("-"));
        }
        return builder.toString();
    }

    /**
     * Into a single key-value map.
     */
    public static Map<String, String> getRequestHeaders(HttpHeaders headers) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String trueValue = StringUtils.join("; ", value);

            headerMap.put(key, trueValue);
        }
        return headerMap;
    }

    public static long getDate(String gmtTime) {
        try {
            return parseGMTToMillis(gmtTime);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getDate(long milliseconds) {
        return formatMillisToGMT(milliseconds);
    }

    public static long getExpiration(String expiresTime) {
        try {
            return parseGMTToMillis(expiresTime);
        } catch (ParseException e) {
            return -1;
        }
    }
    /**
     * Analysis the headers of the cache is valid time.
     *
     * @param headers http headers header.
     * @return Time corresponding milliseconds.
     */
    public static long analysisCacheExpires(HttpHeaders headers) {
        final long now = System.currentTimeMillis();

        long maxAge = 0;
        long staleWhileRevalidate = 0;

        String cacheControl = headers.getCacheControl( );
        if (!StringUtils.isEmpty(cacheControl)) {
            StringTokenizer tokens = new StringTokenizer(cacheControl, ",");
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken().trim().toLowerCase(Locale.getDefault());
                if ((token.equals("no-cache") || token.equals("no-store"))) {
                    return 0;
                } else if (token.startsWith("max-age=")) {
                    maxAge = Long.parseLong(token.substring(8)) * 1000L;
                } else if (token.startsWith("must-revalidate")) {
                    // If must-revalidate, It must be from the server to validate expired.
                    return 0;
                } else if (token.startsWith("stale-while-revalidate=")) {
                    staleWhileRevalidate = Long.parseLong(token.substring(23)) * 1000L;
                }
            }
        }

        long localExpire = now;// Local expires time of cache.

        // Have CacheControl.
        if (!StringUtils.isEmpty(cacheControl)) {
            localExpire += maxAge;
            if (staleWhileRevalidate > 0) {
                localExpire += staleWhileRevalidate;
            }

            return localExpire;
        }

        final long expires = headers.getExpires();
        final long date = headers.getDate();

        // If the server through control the cache Expires.
        if (expires > date) {
            return now + expires - date;
        }
        return 0;
    }

    public static long getLastModified(String lastModified) {
        try {
            return parseGMTToMillis(lastModified);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static void setAcceptLanguage(String language) {
        acceptLanguage = language;
    }

    /**
     * {@value #KEY_CACHE_CONTROL}.
     *
     * @return CacheControl.
     */
    public String getCacheControl() {
        List<String> cacheControls = get(KEY_CACHE_CONTROL);
        if (cacheControls == null) cacheControls = Collections.emptyList();
        return StringUtils.join(",", cacheControls);
    }
    /**
     * {@value KEY_CONTENT_DISPOSITION}.
     *
     * @return {@value KEY_CONTENT_DISPOSITION}.
     */
    public String getContentDisposition() {
        return getFirst(KEY_CONTENT_DISPOSITION);
    }

    /**
     * {@value #KEY_CONTENT_ENCODING}.
     *
     * @return ContentEncoding.
     */
    public String getContentEncoding() {
        return getFirst(KEY_CONTENT_ENCODING);
    }

    /**
     * {@value #KEY_CONTENT_LENGTH}.
     *
     * @return ContentLength.
     */
    public long getContentLength() {
        String contentLength = getFirst(KEY_CONTENT_LENGTH);
        if (StringUtils.isEmpty(contentLength)) contentLength = "0";
        return Long.parseLong(contentLength);
    }

    /**
     * {@value #KEY_CONTENT_TYPE}.
     *
     * @return ContentType.
     */
    public String getContentType() {
        return getFirst(KEY_CONTENT_TYPE);
    }

    /**
     * {@value #KEY_CONTENT_RANGE}.
     *
     * @return ContentRange.
     */
    public String getContentRange() {
        return getFirst(KEY_CONTENT_RANGE);
    }

    /**
     * {@value #KEY_DATE}.
     *
     * @return Date.
     */
    public long getDate() {
        return getDateField(KEY_DATE);
    }
    /**
     * <p>
     * Returns the date value in milliseconds since 1970.1.1, 00:00h corresponding to the header field field. The
     * defaultValue will be returned if no such field can be found in the headers header.
     * </p>
     *
     * @param key the header field name.
     * @return the header field represented in milliseconds since January 1, 1970 GMT.
     */
    private long getDateField(String key) {
        String value = getFirst(key);
        if (!StringUtils.isEmpty(value))
            try {
                return parseGMTToMillis(value);
            } catch (ParseException ignored) {
            }
        return 0;
    }

    /**
     * {@value #KEY_E_TAG}.
     *
     * @return ETag.
     */
    public String getETag() {
        return getFirst(KEY_E_TAG);
    }

    /**
     * {@value #KEY_EXPIRES}.
     *
     * @return Expiration.
     */
    public long getExpires() {
        return getDateField(KEY_EXPIRES);
    }

    /**
     * {@value #KEY_LAST_MODIFIED}.
     *
     * @return LastModified.
     */
    public long getLastModified() {
        return getDateField(KEY_LAST_MODIFIED);
    }

    /**
     * {@value #KEY_LOCATION}.
     *
     * @return Location.
     */
    public String getLocation() {
        return getFirst(KEY_LOCATION);
    }

    /**
     * Accept-Language: zh-CN,zh;q=0.8
     */
    public static String getAcceptLanguage() {
        if (TextUtils.isEmpty(acceptLanguage)) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuilder acceptLanguageBuilder = new StringBuilder(language);
            if (!TextUtils.isEmpty(country))
                acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8");
            acceptLanguage = acceptLanguageBuilder.toString();
            return acceptLanguage;
        }
        return acceptLanguage;
    }

    public static void setUserAgent(String agent) {
        userAgent = agent;
    }

    /**
     * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
     */
    public static String getUserAgent(Context context) {
        if (TextUtils.isEmpty(userAgent)) {
            String webUserAgent = null;
            try {
                Class<?> sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = context.getString(resId);
            } catch (Exception e) {
                // We have nothing to do
            }
            if (TextUtils.isEmpty(webUserAgent)) {
                webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 %sSafari/533.1";
            }

            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            // Add version
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                // default to "1.0"
                buffer.append("1.0");
            }
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase(locale));
                final String country = locale.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase(locale));
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
            // add the model for the release build
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
            userAgent = String.format(webUserAgent, buffer, "Mobile ");
            return userAgent;
        }
        return userAgent;
    }

    private static long parseGMTToMillis(String gmtTime) throws ParseException {
        if (TextUtils.isEmpty(gmtTime)) return 0;
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US);
        formatter.setTimeZone(GMT_TIME_ZONE);
        Date date = formatter.parse(gmtTime);
        return date.getTime();
    }

    private static String formatMillisToGMT(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US);
        simpleDateFormat.setTimeZone(GMT_TIME_ZONE);
        return simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    public final String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        try {
            for (Map.Entry<String, List<String>> entry : toMap().entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage() + "");
        }
        return jsonObject.toString();
    }

}