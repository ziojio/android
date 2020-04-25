
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebugTree extends Tree {
    private static final int MAX_LOG_LENGTH = 3096;
    private static final int MAX_TAG_LENGTH = 23;
    private static final int CALL_STACK_INDEX = 4;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$"); // 去除匿名内部类的后缀

    @Override
    protected final String getTag() {
        String tag = super.getTag();
        if (tag != null) {
            return tag;
        }
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length <= CALL_STACK_INDEX) {
            throw new IllegalStateException("stacktrace didn't have enough elements.");
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
    }

    /**
     * Extract the tag which should be used for the message from the {@code element}.
     * By default, this will use the class name without any anonymous class suffixes
     * (e.g., {@code Foo$1} becomes {@code Foo}).
     */
    private String createStackElementTag(@NonNull StackTraceElement element) {
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

    @Override
    protected boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    /**
     * Break up {@code message} into maximum-length chunks (if needed) and send to either
     * {@link Log#println(int, String, String) Log.println()} or
     * {@link Log#wtf(String, String) Log.wtf()} for logging.
     * <p>
     * {@inheritDoc}
     */
    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (message.length() < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(tag, message);
            } else {
                Log.println(priority, tag, message);
            }
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part);
                } else {
                    Log.println(priority, tag, part);
                }
                i = end;
            } while (i < newline);
        }
    }
}