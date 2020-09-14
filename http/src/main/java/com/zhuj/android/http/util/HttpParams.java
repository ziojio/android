package com.zhuj.android.http.util;

import com.zhuj.android.http.ListMap;
import com.zhuj.android.http.callback.IProgressResponseCallBack;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;

public class HttpParams<K, V> extends ListMap<K, V> implements Serializable {
    public HttpParams() {
        super(new HashMap<>());
    }

    public HttpParams(Map<K, List<V>> source) {
        super(source);
    }

    public HttpParams(K key, V value) {
        super(new HashMap<>());
        add(key, value);
    }

    public HttpParams<K, V> addAll(HttpParams<K, V> params) {
        if (params != null) {
            for (Map.Entry<K, List<V>> entry : params.entrySet()) {
                K key = entry.getKey();
                List<V> values = entry.getValue();
                for (V value : values) {
                    add(key, value);
                }
            }
        }
        return this;
    }

    public HttpParams<K, V> addAll(Map<K, V> params) {
        if (params != null) {
            for (Map.Entry<K, V> entry : params.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                add(key, value);
            }
        }
        return this;
    }

    // /**
    //  * 存放文件键值对参数
    //  *
    //  * @param key              关键字
    //  * @param file             文件
    //  * @param responseCallBack 上传进度条回调接口
    //  * @param <T>
    //  */
    // public <T extends File> void put(String key, T file, IProgressResponseCallBack responseCallBack) {
    //     put(key, file, file.getName(), responseCallBack);
    // }
    //
    // /**
    //  * 存放文件键值对参数
    //  *
    //  * @param key              关键字
    //  * @param file             文件
    //  * @param fileName         文件名
    //  * @param responseCallBack 上传进度条回调接口
    //  * @param <T>
    //  */
    // public <T extends File> void put(String key, T file, String fileName, IProgressResponseCallBack responseCallBack) {
    //     put(key, file, fileName, guessMimeType(fileName), responseCallBack);
    // }
    //
    // /**
    //  * 存放文件键值对参数
    //  *
    //  * @param key              关键字
    //  * @param file             文件流
    //  * @param fileName         文件名
    //  * @param responseCallBack 上传进度条回调接口
    //  * @param <T>
    //  */
    // public <T extends InputStream> void put(String key, T file, String fileName, IProgressResponseCallBack responseCallBack) {
    //     put(key, file, fileName, guessMimeType(fileName), responseCallBack);
    // }

    /**
     * 解析文件的媒体类型
     *
     * @param path
     */
    private MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }

    /**
     * 文件类型的包装类
     */
    public static class FileWrapper<V> {
        public V file;// 可以是
        public String fileName;
        public MediaType contentType;
        public long fileSize;
        public IProgressResponseCallBack responseCallBack;

        public FileWrapper(V file, String fileName, MediaType contentType, IProgressResponseCallBack responseCallBack) {
            this.file = file;
            this.fileName = fileName;
            this.contentType = contentType;
            if (file instanceof File) {
                this.fileSize = ((File) file).length();
            } else if (file instanceof byte[]) {
                this.fileSize = ((byte[]) file).length;
            }
            this.responseCallBack = responseCallBack;
        }

        @Override
        public String toString() {
            return "FileWrapper{" + "content=" + file + ", fileName='" + fileName + ", contentType=" + contentType + ", fileSize=" + fileSize + '}';
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<K, List<V>> entry : entrySet()) {
            if (result.length() > 0) result.append("&");
            for (V v : entry.getValue()) result.append(entry.getKey()).append("=").append(v);
        }
        return result.toString();
    }
}