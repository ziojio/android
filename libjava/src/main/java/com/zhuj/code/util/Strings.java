package com.zhuj.code.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
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
        return String.format(Locale.CHINA, str, args);
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    public static String quote(String str) {
        return '\'' + str + '\'';
    }

    public static String bracket(String str) {
        return '[' + str + ']';
    }

    public static String unbracket(String str) {
        int len = str.length() - 1;
        if (str.charAt(0) == '[' && str.charAt(len) == ']') {
            return str.substring(1, len - 1);
        }
        if (str.charAt(0) == '[') {
            return str.substring(1);
        }
        if (str.charAt(len) == ']') {
            return str.substring(0, len - 1);
        }
        return str;
    }

    public static String unquote(String str) {
        int len = str.length() - 1;
        if (str.charAt(0) == '\'' && str.charAt(len) == '\'') {
            return str.substring(1, len - 1);
        }
        if (str.charAt(0) == '\'') {
            return str.substring(1);
        }
        if (str.charAt(len) == '\'') {
            return str.substring(0, len - 1);
        }
        return str;
    }

    public static String dquote(String str) {
        return '"' + str + '"';
    }

    public static String undquote(String str) {
        int len = str.length() - 1;
        if (str.charAt(0) == '"' && str.charAt(len) == '"') {
            return str.substring(1, len - 1);
        }
        if (str.charAt(0) == '"') {
            return str.substring(1);
        }
        if (str.charAt(len) == '"') {
            return str.substring(0, len - 1);
        }
        return str;
    }

    /**
     * 过滤字符串中的空格
     *
     * @param str
     */
    public static String replaceBlank(final String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static String trim(String str) {
        String ts = str == null ? null : str.trim();
        return isEmpty(ts) ? "" : ts;
    }

    public static String trimToNull(String str) {
        String ts = str == null ? null : str.trim();
        return isEmpty(ts) ? null : ts;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlankChar(String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
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

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
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

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     */
    public static int toInt(String value) {
        return toInt(value, 0);
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static int toInt(String value, int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     */
    public static float toFloat(String value) {
        return toFloat(value, 0);
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static float toFloat(String value, float defValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Short（防止崩溃）
     *
     * @param value
     */
    public static short toShort(String value) {
        return toShort(value, (short) 0);
    }

    /**
     * String转Short（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static short toShort(String value, short defValue) {
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     */
    public static long toLong(String value) {
        return toLong(value, 0);
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static long toLong(String value, final long defValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Double（防止崩溃）
     *
     * @param value
     */
    public static double toDouble(String value) {
        return toDouble(value, 0);
    }

    /**
     * String转Double（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static double toDouble(String value, double defValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * String转Boolean（防止崩溃）
     *
     * @param value
     */
    public static boolean toBoolean(String value) {
        return toBoolean(value, false);
    }

    /**
     * String转Boolean（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     */
    public static boolean toBoolean(String value, boolean defValue) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是双精度浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) + 32) + s.substring(1);
    }

    /**
     * 过滤字符串中所有的特殊字符
     *
     * @param str
     */
    public static String replaceSpecialCharacter(final String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    /**
     * 过滤字符串中的[和]
     *
     * @param str
     */
    public static String replaceBracket(final String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[\\[\\]]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 获取异常栈信息，不同于Log.getStackTraceString()，该方法不会过滤掉UnknownHostException.
     *
     * @param t {@link Throwable}
     * @return 异常栈里的信息
     */
    public static String getStackTraceString(Throwable t) {
        if (t == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 将对象转化为String
     *
     * @param object
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    /**
     * 获取对象的类名
     *
     * @param object
     */
    public static String getSimpleName(Object object) {
        return object != null ? object.getClass().getSimpleName() : "NULL";
    }

    /**
     * 获取对象的类名
     *
     * @param object
     */
    public static String getName(Object object) {
        return object != null ? object.getClass().getName() : "NULL";
    }

}


