package com.zhuj.code.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;

import okio.Okio;

public class IOUtils {

    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final String FILE_SEPARATOR = File.separator;
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_MB * ONE_KB;
    public static final long ONE_TB = ONE_GB * ONE_KB;

    //****************** Transform ************************
    public static BufferedInputStream toBufferedInputStream(InputStream inputStream) {
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedOutputStream toBufferedOutputStream(OutputStream outputStream) {
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedWriter toBufferedWriter(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static InputStream toInputStream(CharSequence input) {
        return new ByteArrayInputStream(input.toString().getBytes());
    }

    public static InputStream toInputStream(CharSequence input, Charset charset) {
        byte[] bytes = input.toString().getBytes(charset);
        return new ByteArrayInputStream(bytes);
    }
    //****************** Transform ************************

    //****************** Read ************************
    public static List<String> readLines(InputStream input, String charset) throws IOException {
        return readLines(input, Charset.forName(charset));
    }

    public static List<String> readLines(InputStream input, Charset charset) throws IOException {
        Reader reader = new InputStreamReader(input, charset);
        return readLines(reader);
    }

    public static List<String> readLines(InputStream input) throws IOException {
        Reader reader = new InputStreamReader(input);
        return readLines(reader);
    }


    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }
    //****************** Read ************************

    //****************** Write ************************

    public static void writeFile(File file, String str, Charset charset) {
        if (!FileCheck.isExists(file)) {
            file.mkdirs();
        }
        FileOutputStream fin = null;
        try {
            fin = new FileOutputStream(file);
            write(fin, str, charset);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fin);
        }
    }

    public static void write(OutputStream output, byte[] data) throws IOException {
        if (data != null) {
            output.write(data);
            output.flush();
        }
    }

    public static void write(Writer output, byte[] data) throws IOException {
        if (data != null) {
            output.write(new String(data));
            output.flush();
        }
    }

    public static void write(Writer output, byte[] data, Charset charset) throws IOException {
        if (data != null) {
            output.write(new String(data, charset));
            output.flush();
        }
    }

    public static void write(Writer output, CharSequence data) throws IOException {
        if (data != null) {
            output.write(data.toString());
            output.flush();
        }
    }

    public static void write(OutputStream output, CharSequence data) throws IOException {
        if (data != null) {
            output.write(data.toString().getBytes());
            output.flush();
        }
    }

    public static void write(OutputStream output, CharSequence data, Charset charset) throws IOException {
        if (data != null) {
            output.write(data.toString().getBytes(charset));
            output.flush();
        }
    }

    public static void write(Reader input, OutputStream output) throws IOException {
        Writer out = new OutputStreamWriter(output);
        write(input, out);
    }

    public static void write(InputStream input, Writer output) throws IOException {
        Reader in = new InputStreamReader(input);
        write(in, output);
    }

    public static void write(Reader input, OutputStream output, Charset charset) throws IOException {
        Writer out = new OutputStreamWriter(output, charset);
        write(input, out);
    }

    public static void write(InputStream input, OutputStream output, Charset charset) throws IOException {
        Reader in = new InputStreamReader(input, charset);
        write(in, output);
    }

    public static void write(InputStream input, Writer output, Charset charset) throws IOException {
        Reader in = new InputStreamReader(input, charset);
        write(in, output);
    }

    public static void write(InputStream input, OutputStream output) throws IOException {
        int len;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
            output.flush();
        }
    }

    public static void write(InputStream input, OutputStream output, int bufferSize) throws IOException {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be greater than zero: " + bufferSize);
        }
        int len;
        byte[] buffer = new byte[bufferSize];
        while ((len = input.read(buffer)) != -1) {
            output.write(buffer, 0, len);
            output.flush();
        }
    }

    public static void write(Reader input, Writer output) throws IOException {
        int len;
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        while (-1 != (len = input.read(buffer))) {
            output.write(buffer, 0, len);
            output.flush();
        }
    }

    public static void write(Reader input, Writer output, int bufferSize) throws IOException {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be greater than zero: " + bufferSize);
        }
        int len;
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        while (-1 != (len = input.read(buffer))) {
            output.write(buffer, 0, len);
            output.flush();
        }
    }
    //****************** Write ************************

    //****************** toString ************************

    public static String toString(String filepath) {
        if (!FileCheck.isExists(filepath)) {
            return null;
        }
        return toString(new File(filepath), UTF_8);
    }

    public static String toString(String filepath, Charset charset) {
        if (!FileCheck.isExists(filepath)) {
            return null;
        }
        return toString(new File(filepath), charset);
    }

    public static String toString(File file) {
        return toString(file, UTF_8);
    }

    public static String toString(File file, Charset charset) {
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
            close(reader);
        }
    }

    public static String toString(InputStream input) throws IOException {
        return new String(toByteArray(input));
    }

    public static String toString(InputStream input, Charset charset) throws IOException {
        return new String(toByteArray(input), charset);
    }

    public static String toString(Reader input) throws IOException {
        return new String(toByteArray(input));
    }

    public static String toString(Reader input, Charset charset) throws IOException {
        return new String(toByteArray(input), charset);
    }

    public static String toString(byte[] byteArray) {
        return new String(byteArray);
    }

    public static String toString(byte[] byteArray, Charset charset) {
        return new String(byteArray, charset);
    }
    //****************** toString ************************

    //****************** to Array ************************
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output);
        output.close();
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, output);
        output.close();
        return output.toByteArray();
    }

    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        write(input, output);
        return output.toCharArray();
    }
    //******************  to Array ************************

    public static void close(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 不打印错误信息
     *
     * @param closeables closeables
     */
    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static void flush(Flushable... flushables) {
        if (flushables == null) return;
        for (Flushable flushable : flushables) {
            if (flushable != null) try {
                flushable.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void flushQuietly(Flushable... flushables) {
        if (flushables == null) return;
        for (Flushable flushable : flushables) {
            if (flushable != null) try {
                flushable.flush();
            } catch (IOException ignored) {
            }
        }
    }

}
