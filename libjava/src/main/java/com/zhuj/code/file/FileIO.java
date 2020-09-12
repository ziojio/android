package com.zhuj.code.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileIO {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final String FILE_SEPARATOR = File.separator;
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    private FileIO() {
    }

    public static String readString(String filepath) {
        if (!FileCheck.isExists(filepath)) {
            return null;
        }
        return readString(new File(filepath), UTF_8);
    }

    public static String readString(String filepath, Charset charset) {
        if (!FileCheck.isExists(filepath)) {
            return null;
        }
        return readString(new File(filepath), charset);
    }

    public static String readString(File file) {
        return readString(file, UTF_8);
    }

    public static String readString(File file, Charset charset) {
        if (!FileCheck.isExists(file)) {
            return null;
        }
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (charset == null) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            }
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEPARATOR).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(reader);
        }
    }

    public static void writeString(String filepath, String str) throws IOException {
        writeString(new File(filepath), str, null, false);
    }

    public static void writeString(String filepath, String str, Charset charset) throws IOException {
        writeString(new File(filepath), str, charset, false);
    }

    public static void writeString(File file, String str) throws IOException {
        writeString(file, str, null, false);
    }

    public static void writeString(File file, String str, Charset charset) throws IOException {
        writeString(file, str, charset, false);
    }

    public static void writeString(File file, String str, Charset charset, boolean append) throws IOException {
        OutputStreamWriter writer;
        if (charset == null) {
            writer = new OutputStreamWriter(new FileOutputStream(file, append));
        } else {
            writer = new OutputStreamWriter(new FileOutputStream(file, append), charset);
        }
        IOUtils.write(writer, str);
        IOUtils.close(writer);
    }


}
