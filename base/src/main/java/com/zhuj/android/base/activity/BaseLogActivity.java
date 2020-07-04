package com.zhuj.android.base.activity;


import android.text.TextUtils;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseLogActivity extends IActivity {
    private String logTag;
    private boolean isShowFileAndLineNumber;

    BaseLogActivity() {
        isShowFileAndLineNumber = true;
    }

    protected void setLogTag(String logTag) {
        this.logTag = logTag;
    }

    protected void setShowFileAndLineNumber(boolean showFileAndLineNumber) {
        isShowFileAndLineNumber = showFileAndLineNumber;
    }

    /**
     * 当前的栈偏移，打印的栈跟踪 指定栈 - 1
     */
    private final int currentStackOffset = 2;
    private final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$"); // 去除匿名内部类的后缀


    public StackTraceElement getStackTraceElement(int stackOffsetIndex) {
        return new Throwable().getStackTrace()[stackOffsetIndex];
    }

    public String getCleanClassName(String className) {
        Matcher m = ANONYMOUS_CLASS.matcher(className);
        if (m.find()) {
            return m.replaceAll("");
        }
        return className;
    }

    public String getMethodString() {
        StackTraceElement element = new Throwable().getStackTrace()[currentStackOffset];
        String msgm = element.getMethodName();
        if (isShowFileAndLineNumber) {
            return msgm + "(" + element.getFileName() + ":" + element.getLineNumber() + ")" + ": ";
        }
        return msgm + ": ";
    }

    private String getLogString(String msg, Object... args) {
        if (args.length == 0) {
            return msg;
        } else {
            return String.format(msg, args);
        }
    }

    private String getTagString() {
        if (!TextUtils.isEmpty(logTag)) {
            return logTag;
        } else {
            return TAG;
        }
    }

    public void logDebug(String msg, Object... args) {
        Log.d(getTagString(), getMethodString() + getLogString(msg, args));
    }

    public void logInfo(String msg, Object... args) {
        Log.i(getTagString(), getMethodString() + getLogString(msg, args));
    }

    public void logWarn(String msg, Object... args) {
        Log.w(getTagString(), getMethodString() + getLogString(msg, args));
    }

    public void logError(String msg, Object... args) {
        Log.e(getTagString(), getMethodString() + getLogString(msg, args));
    }

    public void logJson(String json) {
        String msg = "json is NULL";
        if (json != null) {
            try {
                msg = new JSONObject(json).toString(4);
            } catch (JSONException e) {
                msg = "json parse ERROR msg=" + e.getMessage();
            }
        }
        Log.d(getTagString(), getMethodString() + "\n" + msg);
    }

}
