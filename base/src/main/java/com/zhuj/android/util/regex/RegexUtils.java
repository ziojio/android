package com.zhuj.android.util.regex;

import com.jbzh.baseapp.util.Strings;

import java.util.regex.Pattern;

public class RegexUtils {
    private static final Pattern COLOR_PATTERN = Pattern.compile("^#([0-9a-fA-F]{2}|)([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$");
    private static final Pattern HTTP_URL = Pattern.compile(RegexConstants.REGEX_URL);

    /**
     * @param colorStr format #FF123456 or #123456
     * @return
     */
    public static boolean isColor(String colorStr) {
        if (Strings.isBlank(colorStr)) {
            return false;
        }
        return COLOR_PATTERN.matcher(colorStr).matches();
    }

    public static boolean isHttpUrl(String url) {
        if (Strings.isBlank(url)) {
            return false;
        }
        return HTTP_URL.matcher(url).matches();
    }
}
