package com.zhuj.code.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileIO {
    private FileIO() {
    }

    public static void writeString(String filepath, String str) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filepath));
        IOUtils.write(IOUtils.toBufferedWriter(writer), str);
        IOUtils.closeQuietly(writer);
    }

    public static void writeString(File file, String str) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
        IOUtils.write(IOUtils.toBufferedWriter(writer), str);
        IOUtils.closeQuietly(writer);
    }
}
