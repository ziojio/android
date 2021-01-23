package zhuj.utils.logger;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class LogcatLogger extends ILogger {
    private static final int MAX_LOG_LENGTH = 3096;

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
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
        Log.w(tag, "--------------------------------------------------------------------------------------------------------------");
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
        Log.e(tag, "--------------------------------------------------------------------------------------------------------------");
    }
}
