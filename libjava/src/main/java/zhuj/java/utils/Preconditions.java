package zhuj.android;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * checkXXX() throw Exception
 * isXXX() return true or false
 *
 */
public final class Preconditions {

    private Preconditions() {
        // Utility class.
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return obj.toString().isEmpty();
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(Object[] array) {
        if (array == null) return true;
        return Array.getLength(array) == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null) return true;
        return map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Collection<?> collection) {
        if (collection == null) return true;
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isPrimitive(Type type) {
        return !(type instanceof Class<?>) || ((Class<?>) type).isPrimitive();
    }

    public static boolean isPrimitiveOrWrapper(Type type) {
        return !(type instanceof Class<?>) || ((Class<?>) type).isPrimitive();
    }

    public static boolean isNotPrimitive(Type type) {
        return !(type instanceof Class<?>) || !((Class<?>) type).isPrimitive();
    }

    public static boolean isWrapClass(Class<?> clazz) {
        try {
            Class<?> type = (Class<?>) clazz.getField("TYPE").get(null);
            return type != null && type.isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkArgument(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
        return true;
    }

    public static boolean checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    public static void checkNotPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || !((Class<?>) type).isPrimitive());
    }

    public static void checkPrimitive(Type type) {
        checkArgument(!(type instanceof Class<?>) || ((Class<?>) type).isPrimitive());
    }

    public static <T> boolean isNull(T arg) {
        return arg == null;
    }

    public static <T> boolean isNotNull(T arg) {
        return arg != null;
    }


    public static <T> T checkNotNull(T arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
        return arg;
    }


    public static <T> T checkNotNull(T arg, String message) {
        if (arg == null) {
            throw new NullPointerException(message);
        }
        return arg;
    }


    public static String checkNotEmpty(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty");
        }
        return string;
    }


    public static String checkNotBlank(String string) {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("Must not be null or blank");
        }
        return string;
    }


    public static <T extends Collection<?>> T checkNotEmpty(T collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("Must not be null or empty.");
        }
        return collection;
    }

    /**
     * Ensures that the argument int value is within the inclusive range.
     *
     * @param value     a int value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated int value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static int checkArgumentInRange(int value, int lower, int upper, String valueName) {
        if (value < lower) {
            throw new IllegalArgumentException(
                    String.format(Locale.US,
                            "%s is out of range of [%d, %d] (too low)", valueName, lower, upper));
        } else if (value > upper) {
            throw new IllegalArgumentException(
                    String.format(Locale.US,
                            "%s is out of range of [%d, %d] (too high)", valueName, lower, upper));
        }
        return value;
    }
}