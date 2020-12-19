package com.zhuj.code.time;

import com.zhuj.code.Preconditions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Times {

    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = 60 * 60;
    public static final int SECONDS_PER_DAY = 60 * 60 * 24;

    public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int MILLISECONDS_PER_MINUTE = 1000 * 60;
    public static final int MILLISECONDS_PER_HOUR = 1000 * 60 * 60;
    public static final int MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;

    /**
     * 字母      含义     示例
     *
     * @param y 年份。一般用 yy 表示两位年份，yyyy 表示 4 位年份;
     *          使用 yy 表示的年扮，如 11；使用 yyyy 表示的年份，如 2011
     * @param M 月份。一般用 MM 表示月份，如果使用 MMM，则会根据语言环境显示不同语言的月份;
     *          使用 MM 表示的月份，如 05；
     *          使用 MMM 表示月份，在 Locale.CHINA语言环境下，如“十月”；在 Locale.US语言环境下，如 Oct
     * @param d 月份中的天数。一般用 dd 表示天数; 使用 dd 表示的天数，如 10
     * @param D 年份中的天数。表示当天是当年的第几天， 用 D 表示	使用 D 表示的年份中的天数，如 295
     * @param E 星期几。用 E 表示，会根据语言环境的不同， 显示不同语言的星期几	使用 E 表示星期几，
     *          在 Locale.CHINA 语言环境下，如“星期四”；在 Locale.US 语言环境下，如 Thu
     * @param H 一天中的小时数（0~23)。一般用 HH 表示小时数	使用 HH 表示的小时数，如 18
     * @param h 一天中的小时数（1~12)。一般使用 hh 表示小时数;
     *          使用 hh 表示的小时数，如 10 (注意 10 有可能是 10 点，也可能是 22 点）
     * @param m 分钟数。一般使用 mm 表示分钟数	使用 mm 表示的分钟数，如 29
     * @param s 秒数。一般使用 ss 表示秒数	使用 ss 表示的秒数，如 38
     * @param S 毫秒数。一般使用 SSS 表示毫秒数, 使用 SSS 表示的毫秒数，如 156
     */
    private Times(String y, String M, String d, String D, String E, String H, String h, String m, String s, String S) {
    }

    public static final String STYLE_yyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";

    public static final SimpleDateFormat FORMAT_yyyMMdd_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_yyyMMdd_HHmmss_2 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_HHmmss = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat FORMAT_HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static Date parseDate(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isToday(Date date) {
        Preconditions.checkNotNull(date);
        Calendar cur = Calendar.getInstance();
        cur.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());

        if (cur.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            int diffDay = cur.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
            return diffDay == 0;
        }
        return false;
    }

}
