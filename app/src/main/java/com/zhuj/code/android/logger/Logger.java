package com.jbzh.android.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
