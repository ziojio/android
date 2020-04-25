package com.jbzh.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Tree {
    final ThreadLocal<String> localTag = new ThreadLocal<>(); // 设置 Tag 在下一次打印时使用, 只使用一次

    public void v(String message, Object... args) {
        prepareLog(Log.VERBOSE, null, message, args);
    }

    public void i(String message, Object... args) {
        prepareLog(Log.INFO, null, message, args);
    }

    public void d(String message, Object... args) {
        prepareLog(Log.DEBUG, null, message, args);
    }

    public void w(String message, Object... args) {
        prepareLog(Log.WARN, null, message, args);
    }

    public void e(String message, Object... args) {
        prepareLog(Log.ERROR, null, message, args);
    }

    /**
     * Log an assert message with optional format args.
     */
    public void wtf(String message, Object... args) {
        prepareLog(Log.ASSERT, null, message, args);
    }

    protected void prepareLog(int priority, Throwable t, String message, Object... args) {
        // Consume tag even when message is not loggable so that next message is correctly tagged.
        String tag = getTag();

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

        log(priority, tag, message, t);
    }

    @Nullable
    protected String getTag() {  // 由子类调用，获取设置的Tag
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
        }
        return tag;
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
    protected abstract void log(int priority, @Nullable String tag, @NonNull String message, @Nullable Throwable t);
}