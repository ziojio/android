package com.zhuj.android.logger;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogcatLogger implements ILogger {

    /**
     * logcat里日志的最大长度.
     */
    private static final int MAX_LOG_LENGTH = 4000;

    private static boolean isShowFileAndLineNumber = true;

    private static int currentStackOffset = 3;

    public static String getLogInMethodFileLine() {
        StackTraceElement element = new Throwable().getStackTrace()[currentStackOffset];
        // if (isShowFullClassMethod) {
        //     msgm = element.getClassName() + "." + element.getMethodName();
        // } else {
        //     msgm = element.getMethodName();
        // }
        if (isShowFileAndLineNumber) {
            return element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + "): ";
        }
        return "";
    }

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     * @param t        出错信息
     */
    @Override
    public void log(int priority, String tag, String message, Throwable t) {
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message == null) {
            if (t == null) {
                return; // Swallow message if it's null and there's no throwable.
            }
            message = getStackTraceString(t);
        } else {
            if (t != null) {
                message += "\n" + getStackTraceString(t);
            }
        }

        log(priority, tag, getLogInMethodFileLine() + message);
    }

    private String getStackTraceString(Throwable tr) {
        // Don't replace this with Log.getStackTraceString() - it hides
        // UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 使用LogCat输出日志，字符长度超过4000则自动换行.
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     */
    public void log(int priority, String tag, String message) {
        int subNum = message.length() / MAX_LOG_LENGTH;
        if (subNum > 0) {
            int index = 0;
            for (int i = 0; i < subNum; i++) {
                int lastIndex = index + MAX_LOG_LENGTH;
                String sub = message.substring(index, lastIndex);
                logSub(priority, tag, sub);
                index = lastIndex;
            }
            logSub(priority, tag, message.substring(index));
        } else {
            logSub(priority, tag, message);
        }
    }

    /**
     * 使用LogCat输出日志.
     *
     * @param priority 优先级
     * @param tag      标签
     * @param sub      信息
     */
    private void logSub(int priority, String tag, String sub) {
        switch (priority) {
            case Log.VERBOSE:
                Log.v(tag, sub);
                break;
            case Log.DEBUG:
                Log.d(tag, sub);
                break;
            case Log.INFO:
                Log.i(tag, sub);
                break;
            case Log.WARN:
                Log.w(tag, sub);
                break;
            case Log.ERROR:
                Log.e(tag, sub);
                break;
            case Log.ASSERT:
                Log.wtf(tag, sub);
                break;
            default:
                Log.v(tag, sub);
                break;
        }
    }

}
