package zhuj.utils.file;

import com.zhuj.code.lang.Strings;

import java.io.File;

public class FileCheck {
    private FileCheck() {
    }

    /**
     * Checks that the given {@code File} exists and is a directory.
     *
     * @param directory The {@code File} to check.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    public static void requireDirectory(String directory) {
        if (Strings.isBlank(directory)) {
            throw new IllegalArgumentException("directory [" + directory + "] is blank");
        }
        requireDirectory(new File(directory));
    }

    public static void requireDirectory(File directory) {
        if (directory == null) {
            throw new IllegalArgumentException("directory [null] is null");
        }
        if (!directory.exists()) {
            throw new IllegalArgumentException("directory [" + directory + "] does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("directory [" + directory + "] is not a directory");
        }
    }

    public static boolean isDirectory(String directory) {
        if (Strings.isBlank(directory)) {
            return false;
        }
        return isDirectory(new File(directory));
    }

    public static boolean isDirectory(File directory) {
        return directory != null && directory.exists() && directory.isDirectory();
    }

    public static void requireFile(String filepath) {
        if (Strings.isBlank(filepath)) {
            throw new IllegalArgumentException("file [" + filepath + "] is blank");
        }
        requireFile(new File(filepath));
    }

    public static void requireFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("directory [null] is null");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("file [" + file + "] does not exist");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("file [" + file + "] does not is file");
        }
    }

    public static boolean isFile(String filepath) {
        if (Strings.isBlank(filepath)) {
            return false;
        }
        return isFile(new File(filepath));
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean isExists(String filepath) {
        return !Strings.isBlankChar(filepath) && new File(filepath).exists();
    }

    public static boolean isExists(File file) {
        return file != null && file.exists();
    }


}
