package zhuj.android.zui.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import zhuj.android.zui.BuildConfig;


/**
 * 同于Android中类的调试
 */
public class ZDebug {
    private static final String TAG = "ZDebugView";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int STACK_ELEMENT_INDEX = 2;

    public static void debug(String tag, String msg, Object... args) {
        if (DEBUG) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.d(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void debug(boolean debug, String tag, String msg, Object... args) {
        if (debug) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.d(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void warn(String tag, String msg, Object... args) {
        if (DEBUG) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.w(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void warn(boolean debug, String tag, String msg, Object... args) {
        if (debug) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.w(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void error(String tag, String msg, Object... args) {
        if (DEBUG) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.e(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void error(boolean debug, String tag, String msg, Object... args) {
        if (debug) {
            if (tag == null) {
                tag = TAG;
            }
            if (args != null && args.length > 0) {
                msg = String.format(msg, args);
            }
            Log.e(tag, getStackElementMethodFileLine(getStackElement(STACK_ELEMENT_INDEX)) + msg);
        }
    }

    public static void error(String tag, Throwable throwable) {
        if (DEBUG) {
            if (tag == null) {
                tag = TAG;
            }
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
