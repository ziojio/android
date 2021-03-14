package zhuj.android.utils.log;

import android.os.Looper;
import android.util.Log;


import java.io.PrintWriter;
import java.io.StringWriter;

import zhuj.android.utils.BuildConfig;


public class ZLog {
    private static String tag = "ZLogUtils";
    private static boolean debug = BuildConfig.DEBUG;
    private static final int STACK_ELEMENT_INDEX = 2;

    public static void initialize(String tag, boolean debug) {
        if (tag != null) {
            ZLog.tag = tag;
        }
        ZLog.debug = debug;
    }

    public static void printUiThread() {
        if (debug) {
            String stackMsg = getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX));
            String threadMsg = Thread.currentThread().toString();
            if (Looper.getMainLooper() == Looper.myLooper()) {
                Log.d(tag, stackMsg + "Current Thread is UI Thread " + threadMsg);
            } else {
                Log.d(tag, stackMsg + "Current Thread is Work Thread " + threadMsg);
            }
        }
    }

    public static void printStackInfo() {
        if (debug) {
            Log.d(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + getStackTraceString(new Throwable()));
        }
    }

    public static void debug(String tag, String msg) {
        if (debug) {
            Log.d(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void debug(String tag, String msg, Object... args) {
        if (debug) {
            Log.d(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + String.format(msg, args));
        }
    }

    public static void warn(String tag, String msg) {
        if (debug) {
            Log.w(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void error(String tag, String msg) {
        if (debug) {
            Log.e(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void error(String tag, Throwable throwable) {
        if (debug) {
            Log.e(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + getStackTraceString(throwable));
        }
    }

    private static String getStackElementMethodFileLine(StackTraceElement element) {
        return element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ") - ";
    }

    private static StackTraceElement getStackElement(int index) {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= index) {
            throw new IllegalStateException("stacktrace didn't have enough elements.");
        }
        return stackTrace[index];
    }

    private static String getStackTraceString(Throwable t) {
        // Don't replace this with Log.getStackTraceString()
        // -- it hides UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }
}
