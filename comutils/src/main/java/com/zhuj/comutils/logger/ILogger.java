package com.zhuj.comutils.logger;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ILogger {
    final ThreadLocal<String> localTag = new ThreadLocal<>(); // 设置 Tag 在下一次打印时使用, 只使用一次
    private final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$"); // 去除匿名内部类的后缀
    private static final int CALL_STACK_INDEX = 4;
    private static final int MAX_TAG_LENGTH = 23;
    protected static boolean isShowFileAndLineNumber = true;

    @Nullable
    protected String getTag() {  // 由子类调用，获取设置的Tag
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return getStackElementTag(getCurrentStackElement());
    }

    private StackTraceElement getCurrentStackElement() {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException("stacktrace didn't have enough elements.");
        }
        return stackTrace[CALL_STACK_INDEX];
    }

    /**
     * Extract the tag which should be used for the message from the {@code element}.
     * By default, this will use the class name without any anonymous class suffixes
     * (e.g., {@code Foo$1} becomes {@code Foo}).
     */
    private String getStackElementTag(@NonNull StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1);
        // Tag length limit was removed in API 24.
        if (tag.length() <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return tag;
        }
        return tag.substring(0, MAX_TAG_LENGTH);
    }

    public String getStackElementMethodFileLine() {
        StackTraceElement element = getCurrentStackElement();
        return element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + "): ";
    }

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param tag      标签
     * @param message  信息
     * @param t        出错信息
     */
    protected void prepareLog(int priority, String tag, Throwable t, String message, Object... args) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        if (tag == null) {
            tag = getTag();
        }

        if (!isLoggable(priority, tag)) {
            return;
        }
        if (message != null && message.length() == 0) {
            message = null;
        }
        if (message == null) {
            if (t == null) {
                return; // Swallow message if it's null and there's no throwable.
            }
            message = getStackTraceString(t);
        } else {
            if (args != null && args.length > 0) {
                message = formatMessage(message, args);
            }
            if (t != null) {
                message += "\n" + getStackTraceString(t);
            }
        }
        if (isShowFileAndLineNumber) {
            message = getStackElementMethodFileLine() + message;
        }

        log(priority, tag, message, t);
    }

    /**
     * Formats a log message with optional arguments.
     */
    protected String formatMessage(@NonNull String message, @NonNull Object[] args) {
        return String.format(message, args);
    }

    protected String getStackTraceString(Throwable t) {
        // Don't replace this with Log.getStackTraceString()
        // -- it hides UnknownHostException, which is not what we want.
        StringWriter sw = new StringWriter(256);
        PrintWriter pw = new PrintWriter(sw, false);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * Return whether a message at {@code priority} or {@code tag} should be logged.
     */
    protected abstract boolean isLoggable(int priority, @Nullable String tag);

    /**
     * Write a log message to its destination. Called for all level-specific methods by default.
     *
     * @param priority Log level. See {@link Log} for constants.
     * @param tag      Explicit or inferred tag. May be {@code null}.
     * @param message  Formatted log message. May be {@code null}, but then {@code t} will not be.
     * @param t        Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
     */
    protected abstract void log(int priority, String tag, String message, @Nullable Throwable t);

}
