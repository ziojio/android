package com.zhuj.code.android.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtil {
    private TextUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmptyTrim(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        int len = str.length();
        int start = 0;
        while ((start < len) && (str.charAt(start) <= ' ')) start++;
        while ((start < len) && (str.charAt(len - 1) <= ' ')) len--;
        return start == len;
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
        StringBuffer retBuf = new StringBuffer();
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
        if (str == null)  return null;
        StringBuffer stringBuffer = new StringBuffer();
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
                    stringBuffer.append(c);
                    i = endNode;
                }
            }
        }
        return stringBuffer.toString();
    }
}
