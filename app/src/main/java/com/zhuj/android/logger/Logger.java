package com.zhuj.android.logger;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;
import static java.util.Collections.unmodifiableList;

public final class Logger {
    private Logger() {
        throw new AssertionError("no instance");
    }

    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static final List<Tree> FOREST = new ArrayList<>();
    private static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    public static void tag(String tag) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.localTag.set(tag);
        }
    }

    /**
     * Add a new logging tree.
     */
    @SuppressWarnings("ConstantConditions") // Validating public API contract.
    public static void plant(@NonNull Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[0]);
        }
    }

    /**
     * Adds new logging trees.
     */
    @SuppressWarnings("ConstantConditions")
    public static void plant(@NonNull Tree... trees) {
        if (trees == null) {
            throw new NullPointerException("trees == null");
        }
        for (Tree tree : trees) {
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            forestAsArray = FOREST.toArray(new Tree[0]);
        }
    }

    /**
     * Remove a planted tree.
     */
    public static void remove(@NonNull Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("this tree is not in forest." + tree);
            }
            forestAsArray = FOREST.toArray(new Tree[0]);
        }
    }

    /**
     * Remove all planted trees.
     */
    public static void removeAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /**
     * Return a not modified copy of all planted {@linkplain Tree trees}.
     */
    @NonNull
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    public static void v(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.v(message, args);
        }
    }

    public static void i(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.i(message, args);
        }
    }

    public static void d(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.d(message, args);
        }
    }

    public static void w(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.w(message, args);
        }
    }

    public static void e(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.e(message, args);
        }
    }

    public static void wtf(String message, Object... args) {
        Tree[] forest = forestAsArray;
        for (Tree tree : forest) {
            tree.wtf(message, args);
        }
    }

    public static void json(@Nullable String json) {
        if (json == null || json.length() == 0) {
            e("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }

    public static void xml(@Nullable String xml) {
        if (xml == null || xml.length() == 0) {
            e("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml");
        }
    }

    public static String logLevel(int value) {
        switch (value) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }

    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }


    public static abstract class Tree {
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

    public static class DebugTree extends Tree {
        private static final int MAX_LOG_LENGTH = 3096;
        private static final int MAX_TAG_LENGTH = 23;
        private static final int CALL_STACK_INDEX = 3;
        private final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$"); // 去除匿名内部类的后缀

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
        @Nullable
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
}
