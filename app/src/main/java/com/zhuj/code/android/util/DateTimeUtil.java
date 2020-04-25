
import java.text.SimpleDateFormat;

public class DateTimeUtil {
    private DateTimeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static long getNow(){
        return System.currentTimeMillis();
    }
    public static String getNowDateTime(String pattern){
//        DateFormat.getDateTimeInstance().format(System.currentTimeMillis());
       return new SimpleDateFormat(pattern).format(System.currentTimeMillis());
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
