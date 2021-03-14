package zhuj.java.log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZLog {
    private final String TAG = "ZLog";
    public static final int ALL = Integer.MIN_VALUE;  // typically mapped to/from j.u.l.Level.ALL
    public static final int OFF = Integer.MAX_VALUE;  // typically mapped to/from j.u.l.Level.OFF
    public static final int TRACE = 400;              // typically mapped to/from j.u.l.Level.FINER
    public static final int DEBUG = 500;              // typically mapped to/from j.u.l.Level.FINEST/FINE/CONFIG
    public static final int INFO = 800;               // typically mapped to/from j.u.l.Level.INFO
    public static final int WARNING = 900;            // typically mapped to/from j.u.l.Level.WARNING
    public static final int ERROR = 1000;             // typically mapped to/from j.u.l.Level.SEVERE

    private int logLevel = ALL;
    private String defTag = TAG;
    private String timeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
    private final ThreadLocal<String> localTag = new ThreadLocal<>();    // 设置 Tag 在下一次打印时使用, 只使用一次
    private final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$"); // 去除匿名内部类的后缀
    private static final int CALL_STACK_INDEX = 4;
    private static final int MAX_TAG_LENGTH = 32;
    protected static boolean isShowFileAndLineNumber = true;

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean isLoggable(int level, String tag) {
        if (level >= logLevel) {
            return true;
        }
        return false;
    }

    /**
     * 设置日志的tag
     *
     * @param tag
     */
    public void setDefaultTag(String tag) {
        defTag = tag;
    }

    /**
     * 设置当前线程日志的tag, 只使用一次， 前提是全局标签为 NULL
     *
     * @param tag
     */
    public void setLocalTag(String tag) {
        localTag.set(tag);
    }

    public void debug(String tag, String msg) {
        prepareLog(DEBUG, tag, msg);
    }

    public void debug(String tag, String msg, Object... args) {
        if (msg == null || msg.length() == 0) {
            return;
        }
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        prepareLog(DEBUG, tag, msg);
    }

    public void debug(String tag, Throwable t) {
        if (t == null) {
            return;
        }
        String msg = getStackTraceString(t);
        prepareLog(DEBUG, tag, msg);
    }

    public void debug(String msg) {
        prepareLog(DEBUG, defTag, msg);
    }

    public void info(String tag, String msg) {
        prepareLog(INFO, tag, msg);
    }

    public void info(String msg) {
        prepareLog(INFO, defTag, msg);
    }

    public void error(String tag, String msg) {
        prepareLog(ERROR, tag, msg);
    }

    public void error(String msg) {
        prepareLog(ERROR, defTag, msg);
    }

    public void error(String tag, String msg, Object... args) {
        if (msg == null || msg.length() == 0) {
            return;
        }
        if (args != null && args.length > 0) {
            msg = String.format(msg, args);
        }
        prepareLog(ERROR, tag, msg);
    }

    public void json(String tag, String json) {
        if (json == null || json.length() == 0) {
            error(tag, "Empty/Null json content");
            return;
        }
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            info(tag, new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement));
        } catch (Exception e) {
            error(tag, "invalid json error: " + e.getMessage());
        }
    }

    public void json(String json) {
        json(defTag, json);
    }

    public void xml(String xml) {
        if (xml == null || xml.length() == 0) {
            error("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            info(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            error("Invalid xml error: " + e.getMessage());
        }
    }

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     */
    protected void prepareLog(int priority, String tag, String message) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        if (tag == null) {
            tag = getTag();
        }
        if (!isLoggable(priority, tag)) {
            return;
        }
        if (message == null || message.length() == 0) {
            return; // Swallow message if it's null and there's no throwable.
        }
        print(priority, tag, message);
    }

    public void print(int level, String tag, String message) {
        String prefix = levelToString(level);
        prefix = prefix + " " + getLogTime();
        if (isShowFileAndLineNumber) {
            prefix = prefix + " " + getStackElementMethodFileLine();
        }
        String info = getInfo();
        if (info != null && info.length() > 0) {
            prefix = prefix + " " + info;
        }
        String log = prefix + " " + tag + ": " + message;
        System.out.println(log);
    }

    public void print(String tag, String message, Object... args) {
        if (message == null || message.length() == 0) {
            message = null;
        }
        if (message != null && args != null && args.length > 0) {
            message = String.format(message, args);
        }
        String log = getLogTime() + " " + tag + ": " + message;
        System.out.println(log);
    }

    SimpleDateFormat dateFormat;

    public String getLogTime() {
        if (timeFormat != null && dateFormat == null) {
            try {
                dateFormat = new SimpleDateFormat(timeFormat, Locale.CHINA);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dateFormat != null) {
            try {
                return dateFormat.format(System.currentTimeMillis());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    protected String getInfo() {
//        Thread thread = Thread.currentThread();
        return "";
    }

    protected String getTag() {  // 获取设置的Tag
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return getStackElementTag(getStackElement(CALL_STACK_INDEX));
    }

    /**
     * Extract the tag which should be used for the message from the {@code element}.
     * By default, this will use the class name without any anonymous class suffixes
     * (e.g., {@code Foo$1} becomes {@code Foo}).
     */
    private String getStackElementTag(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1);
        // Tag length limit was removed in API 24.
        if (tag.length() <= MAX_TAG_LENGTH) {
            return tag;
        }
        return tag.substring(0, MAX_TAG_LENGTH);
    }

    public String getStackElementMethodFileLine() {
        StackTraceElement element = getStackElement(CALL_STACK_INDEX + 1);
        return element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
    }

    private StackTraceElement getStackElement(int index) {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= index) {
            throw new IllegalStateException("stacktrace didn't have enough elements.");
        }
        return stackTrace[index];
    }

    public String getStackTraceString(Throwable t) {
        // Don't replace this with Log.getStackTraceString()
        // -- it hides UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    private String levelToString(int level) {
        String info = "[%5s]";
        String arg = "";
        if (level == TRACE) {
            arg = "TRACE";
        } else if (level == DEBUG) {
            arg = "DEBUG";
        } else if (level == INFO) {
            arg = "INFO";
        } else if (level == WARNING) {
            arg = "WARNING";
        } else if (level == ERROR) {
            arg = "ERROR";
        }
        return String.format(info, arg);
    }

    private void showStackTraceElementInfo(StackTraceElement[] stackTrace) {
        for (StackTraceElement stackTraceElement : stackTrace) {
            print(TAG, "--------------------------------------------------------------");
            print(TAG, stackTraceElement.toString());
            print(TAG, "getLineNumber: " + stackTraceElement.getLineNumber());
            print(TAG, " getClassName: " + stackTraceElement.getClassName());
            print(TAG, "getMethodName: " + stackTraceElement.getMethodName());
        }
    }
}