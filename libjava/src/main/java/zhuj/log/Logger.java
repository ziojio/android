package zhuj.log;


public class Logger {

    private static String logName = "Demo";
    private static java.util.logging.Logger logger;

    public static void setLogger(String name) {
        if (name == null) {
            logger = java.util.logging.Logger.getLogger(logName);
        } else {
            logger = java.util.logging.Logger.getLogger(name);
        }
    }

    public static void error(String tag, String msg) {

    }

    public static void error(String tag, String msg, Object... args) {

    }
}
