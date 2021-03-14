package zhuj.java.file;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileNameUtils {
    public static final Set<String> imageSuffixSet = new HashSet<>();
    public static final Set<String> videoSuffixSet = new HashSet<>();
    public static final Set<String> audioSuffixSet = new HashSet<>();
    public static final Set<String> MSDocSet = new HashSet<>();
    public static final Set<String> TextSet = new HashSet<>();

    static {
        TextSet.add("txt");
        TextSet.add("csv");
        TextSet.add("md");
    }

    static {
        MSDocSet.add("doc");
        MSDocSet.add("docx");
        MSDocSet.add("ppt");
        MSDocSet.add("pptx");
        MSDocSet.add("xls");
    }

    static {
        audioSuffixSet.add("aac");
        audioSuffixSet.add("mp3");
        audioSuffixSet.add("cda");
        audioSuffixSet.add("wav");
        audioSuffixSet.add("amr");
        audioSuffixSet.add("wma");
        audioSuffixSet.add("flac");
        audioSuffixSet.add("mid");
        audioSuffixSet.add("ogg");
        audioSuffixSet.add("ape");
        audioSuffixSet.add("aiff");
        audioSuffixSet.add("au");
    }

    static {
        imageSuffixSet.add("jpg");
        imageSuffixSet.add("jpeg");
        imageSuffixSet.add("png");
        imageSuffixSet.add("bmp");
        imageSuffixSet.add("gif");
    }

    static {
        videoSuffixSet.add("mp4");
        videoSuffixSet.add("mkv");
        videoSuffixSet.add("avi");
        videoSuffixSet.add("mpg");
        videoSuffixSet.add("mpeg");
        videoSuffixSet.add("wmv");
        videoSuffixSet.add("mov");
        videoSuffixSet.add("flv");
        videoSuffixSet.add("asf");
        videoSuffixSet.add("rm");
        videoSuffixSet.add("rmvb");
        videoSuffixSet.add("vob");
        videoSuffixSet.add("ts");
        videoSuffixSet.add("dat");
    }

    public static boolean isPdf(String filename) {
        return "pdf".equals(getSuffix(filename));
    }

    public static boolean isText(String filename) {
        return TextSet.contains(getSuffix(filename));
    }

    public static boolean isMsDoc(String filename) {
        return MSDocSet.contains(getSuffix(filename));
    }

    public static boolean isAudio(String filename) {
        return audioSuffixSet.contains(getSuffix(filename));
    }

    public static boolean isImage(String filename) {
        return imageSuffixSet.contains(getSuffix(filename));
    }

    public static boolean isVideo(String filename) {
        return videoSuffixSet.contains(getSuffix(filename));
    }

    public static String getSuffix(String filename) {
        if (filename == null) {
            return "";
        }
        int i = filename.lastIndexOf('.');
        if (i == -1) {
            return "";
        }
        return filename.substring(i + 1).toLowerCase();
    }

    public static String getSuffixWithDot(String filename) {
        if (filename == null) {
            return "";
        }
        int i = filename.lastIndexOf('.');
        if (i == -1) {
            return "";
        }
        return filename.substring(i).toLowerCase();
    }

    public static String getName(String filename) {
        if (filename == null) {
            return "";
        }
        String name = getFileName(filename);
        int i = name.lastIndexOf('.');
        if (i == -1) {
            return name;
        }
        return name.substring(0, i);
    }

    public static String getFileName(String pathname) {
        if (pathname == null || pathname.isEmpty()) {
            return "";
        }
        int i = pathname.lastIndexOf(File.separator);
        if (i != -1) {
            return pathname.substring(i + 1);
        }
        return pathname;
    }
}
