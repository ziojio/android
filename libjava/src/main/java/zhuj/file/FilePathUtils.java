package zhuj.file;

import java.io.File;

import zhuj.lang.Strings;


public class FilePathUtils {

    public static String getPath(File file) {
        return file == null ? "" : file.getAbsolutePath();
    }

    /**
     * 获取文件目录的路径， 补齐"/"
     *
     * @param dir 目录路径
     * @return 补齐 "/" 的目录路径
     */
    public static String addSeparatorIfNo(String dir) {
        if (dir == null) return null;
        if (dir.charAt(dir.length() - 1) != File.separatorChar) {
            return dir + File.separator;
        }
        return dir;
    }

    public static String removeSeparatorIfHas(String dir) {
        if (dir.charAt(dir.length() - 1) == File.separatorChar) {
            return dir.substring(0, dir.length() - 1);
        }
        return dir;
    }

    public static String getDirFromFile(File file) {
        String dir = Strings.trimNotNull(file.getAbsolutePath());
        if (dir.isEmpty()) {
            return "";
        }
        return dir;
    }

    public static String getDirFromPath(String path) {
        String dir = Strings.trimNotNull(path);
        if (dir.isEmpty()) {
            return "";
        }
        int i = dir.lastIndexOf(File.separatorChar);
        if (i == -1) {
            return path;
        }
        return dir;
    }

    public static String getFilePath(String dir, String name) {
        return addSeparatorIfNo(dir) + name;
    }


}
