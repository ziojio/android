package com.zhuj.android.util.folderpicker;


public class FileStorage {
    private String path;
    private StorageType type;

    public FileStorage(String path, StorageType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public StorageType getType() {
        return type;
    }

    public enum StorageType {Internal, External}
}
