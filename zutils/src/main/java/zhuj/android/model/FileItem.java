package zhuj.android.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FileItem {
    public enum FileType {
        FILE,
        IMAGE,
        VIDEO,
        AUDIO,
    }

    int id;
    FileType fileType;
    String path;
    String name;
    String fileExt;
    long time;

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        if (name == null && path != null) {
            int x = path.indexOf("/");
            if (x == -1) x = path.indexOf("\\");
            if (x != -1) name = path.substring(x + 1);
        }
        return name;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFileExt() {
        if (fileExt == null && getName() != null) {
            String s = getName();
            int x = s.indexOf(".");
            if (x != -1) name = s.substring(x + 1);
        }
        return fileExt;
    }

    String extras;

    Map<String, String> extrasMap;

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getExtras() {
        return extras;
    }


    public void setExtrasMap(Map<String, String> extrasMap) {
        this.extrasMap = extrasMap;
    }

    public Map<String, String> getExtrasMap() {
        return extrasMap;
    }

    public void putExtrasItem(String key, String obj) {
        if (extrasMap == null) extrasMap = new LinkedHashMap<>();
        extrasMap.put(key, obj);
    }

    public String getExtrasItem(String key) {
        return extrasMap != null ? extrasMap.get(key) : null;
    }

}
