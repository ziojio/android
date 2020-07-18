package com.zhuj.code.util;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final String EMPTY_STRING = "";

    private Strings() {
    }

    public static String format(String str, Object... args) {
        return String.format(str, args);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmptyTrim(String s) {
        return s == null || s.trim().length() == 0;
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

    public static String decodeUnicode1(String str) {
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

    public static String join(CharSequence delimiter, Iterable tokens) {
        final Iterator<?> it = tokens.iterator();
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

    public static String[] split(String text, String expression) {
        if (text.length() == 0) {
            return EMPTY_STRING_ARRAY;
        } else {
            return text.split(expression, -1);
        }
    }

    public static String[] split(String text, Pattern pattern) {
        if (text.length() == 0) {
            return EMPTY_STRING_ARRAY;
        } else {
            return pattern.split(text, -1);
        }
    }

}
