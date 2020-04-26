package com.zhuj.code.android.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DateTimeUtil {
    private DateTimeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String STYLE_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String STYLE_yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";
    public static final String STYLE_yyyyMMdd_HH_mm_ss = "yyyyMMdd HH:mm:ss";

    public static final String STYLE_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String STYLE_yyyy_MM_dd_HH_mm_ss_1 = "yyyy/MM/dd HH:mm:ss";
    public static final String STYLE_yyyy_MM_dd_HH_mm_ss_2 = "yyyy_MM_dd HH:mm:ss";

    public static long getNow(){
        return System.currentTimeMillis();
    }
    public static String getNowString(String pattern){
       return new SimpleDateFormat(pattern).format(System.currentTimeMillis());
    }
    public static String getNowString(){
       return  DateFormat.getDateTimeInstance().format(System.currentTimeMillis());
    }

    /**
     * 格式化秒数为 HH:ss 格式
     * @param seconds 要小于3600（60*60）
     * @return
     */
    public static String formatSecondsToMMss(int seconds) {
        if (seconds >= 3599) return "59:59";
        int m = seconds / 60;
        int s = seconds % 60;
        return (m > 9 ? m : "0" + m) + ":" + (s > 9 ? s : "0" + s);
    }
}
