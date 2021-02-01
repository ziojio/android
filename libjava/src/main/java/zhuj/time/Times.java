package zhuj.time;

import zhuj.android.Preconditions;
import zhuj.lang.Strings;

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
    public static final SimpleDateFormat FORMAT_time_str = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());


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

    public static boolean isThisYear(Date date) {
        Preconditions.checkNotNull(date);
        Calendar cur = Calendar.getInstance();
        cur.setTimeInMillis(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());

        return cur.get(Calendar.YEAR) == cal.get(Calendar.YEAR);
    }

    /**
     * @param time
     * @return
     */
    public static String formatSecondHHSS(int time) {
        int min = time / 60; // 分
        int sec = time % 60; // 秒
        StringBuilder result = new StringBuilder();
        if (min < 10) {
            result.append("0");
        }
        result.append(min).append(":");
        if (sec < 10) {
            result.append("0");
        }
        result.append(sec);
        return result.toString();
    }

    /**
     * @param time
     * @return
     */
    public static String formatSecondHHSSCN(int time) {
        int min = time / 60; // 分
        int sec = time % 60; // 秒
        return min + "分" + sec + "秒";
    }

    /**
     * @param time
     * @return
     */
    public static String formatSecond(int time) {
        int min = 0; // 分
        int sec = 0; // 小时
        int hour = 0; // 小时
        int day = 0; // 天
        if (time > 60) {
            min = time / 60;
            sec = time % 60;
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
                if (hour > 24) {
                    // 大于24小时
                    day = hour / 24;
                    hour = hour % 24;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        if (day > 0) {
            result.append(day).append("天");
        }
        if (hour > 0) {
            result.append(hour).append("小时");
        }
        if (min > 0) {
            result.append(min).append("分种");
        }
        if (sec > 0) {
            result.append(sec).append("秒");
        }
        return result.toString();
    }

    public static String formatSecond2(int time, String suffix_for_h, String m, String s) {
        int min = 0; // 分
        int sec = 0; // 小时
        int hour = 0; // 小时
        if (time > 60) {
            min = time / 60;
            sec = time % 60;
            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }
        } else {
            sec = time;
        }
        StringBuilder result = new StringBuilder();
        if (hour > 0) {
            result.append(hour).append("suffix_for_h");
        }
        if (min > 0) {
            result.append(min).append("m");
        }
        if (sec > 0) {
            result.append(sec).append("s");
        }
        return result.toString();
    }

    /**
     * format 01:01
     *
     * @param time
     * @return
     */
    public static String formatSecond3(int time) {
        int min = 0; // 分
        int sec;
        if (time > 60) {
            min = time / 60;
            sec = time % 60;
        } else {
            sec = time;
        }
        return Strings.format("%02d:%02d", min, sec);
    }

}
