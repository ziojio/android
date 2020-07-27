package com.zhuj.code.util;

import java.io.File;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {
    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final String FILE_SEPARATOR = File.separator;
    public static final String LINE_SEPARATOR = System.lineSeparator();

    private Strings() {
    }

    public static String format(String str, Object... args) {
        return String.format(str, args);
    }

    public static boolean isEmptyTrim(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String quote(String str) {
        return '\'' + str + '\'';
    }

    public static String trimToNull(String str) {
        String ts = str == null ? null : str.trim();
        return isEmpty(ts) ? null : ts;
    }

    public static String dquote(String str) {
        return '"' + str + '"';
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static String[] split(String text, String expression) {
        if (isEmpty(text)) {
            return EMPTY_STRING_ARRAY;
        } else {
            return text.split(expression, -1);
        }
    }

    public static String[] split(String text, Pattern pattern) {
        if (isEmpty(text)) {
            return EMPTY_STRING_ARRAY;
        } else {
            return pattern.split(text, -1);
        }
    }

    public static String join(CharSequence delimiter, Object[] tokens) {
        final int length = tokens.length;
        if (length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(tokens[0]);
        for (int i = 1; i < length; i++) {
            sb.append(delimiter);
            sb.append(tokens[i]);
        }
        return sb.toString();
    }

    public static String join(CharSequence delimiter, Iterable<?> iterator) {
        final Iterator<?> it = iterator.iterator();
        if (!it.hasNext()) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(it.next());
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static String join(Iterable<?> iterable, char separator) {
        return iterable == null ? null : join(iterable.iterator(), separator);
    }

    public static String join(Iterator<?> iterator, char separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return Objects.toString(first, "");
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while (iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String toRootUpperCase(String str) {
        return str.toUpperCase(Locale.ROOT);
    }

    public static String decodeUnicodeRegex(String str) {
        final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
        Matcher m = reUnicode.matcher(str);
        StringBuffer sb = new StringBuffer(str.length());
        while (m.find()) {
            m.appendReplacement(sb, Character.toString((char) Integer.parseInt(m.group(1), 16)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String decodeUnicode(String str) {
        if (str == null) return null;
        StringBuilder retBuf = new StringBuilder();
        int maxLoop = str.length();
        for (int i = 0; i < maxLoop; i++) {
            if (str.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((str.charAt(i + 1) == 'u') || (str.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(str.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(str.charAt(i));
                    }
                else
                    retBuf.append(str.charAt(i));
            } else {
                retBuf.append(str.charAt(i));
            }
        }
        return retBuf.toString();
    }

    public static String decodeUnicode2(String str) {
        if (str == null) return null;
        StringBuilder sb = new StringBuilder();
        int maxLoop = str.length();
        for (int i = 0; i < maxLoop; i++) {
            if (str.charAt(i) == '&' && str.charAt(i + 1) == '#') {
                int endNode = -1; // 结束节点.
                for (int j = i + 2; j < i + 10; j++) {
                    if (str.charAt(j) == ';') {
                        endNode = j;
                        break;
                    }
                }
                if (endNode != -1) {
                    char c = (char) Integer.parseInt(str.substring(i + 2, endNode), 10);
                    sb.append(c);
                    i = endNode;
                }
            }
        }
        return sb.toString();
    }
}


