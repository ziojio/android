package zhuj.http.util;

import com.google.gson.Gson;
import zhuj.http.ssl.SSL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class EasyHttp {
    private static OkHttpClient okHttpClient = null;

    private static synchronized OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            //缓存目录
            // File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
            // int cacheSize = 10 * 1024 * 1024;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder = builder.sslSocketFactory(new SSL(trustAllCert), trustAllCert); //忽略安全证书认证

            okHttpClient = builder
                    .connectTimeout(1500, TimeUnit.SECONDS)
                    .writeTimeout(2000, TimeUnit.SECONDS)
                    .readTimeout(2000, TimeUnit.SECONDS)
                    //.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                    .build();
        }
        return okHttpClient;
    }

    private static X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };

    /**
     * 监听文件的构造进度
     *
     * @param file
     * @param listener
     * @return
     */
    private static RequestBody buildFileRequestBody(final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.get("multipart/form-data; charset=utf-8");
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) {
                if (listener == null) {
                    try (Source source = Okio.source(file)) {
                        sink.writeAll(source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Source source = Okio.source(file);
                        Buffer buf = new Buffer();
                        long readCount = 0;
                        long len;
                        while ((len = source.read(buf, 2048)) != -1) {
                            sink.write(buf, len);
                            readCount += len;
                            listener.onProgress(readCount * 100 / contentLength());
                        }
                    } catch (IOException ignored) {
                    }
                }
            }
        };
    }


    public interface ProgressListener {

        /**
         * 开始下载
         *
         * @param size 文件大小
         */
        void onStart(long size);

        /**
         * 下载的字节位置
         *
         * @param index
         */
        void onProgress(long index);

        /**
         * 下载失败
         *
         * @param throwable
         */
        void onFailure(Throwable throwable);

        /**
         * 下载完成
         *
         * @param file
         */
        void onDone(File file);
    }


    private static void saveFile(File file, Response response, ProgressListener listener) {
        InputStream is = null;
        byte[] buf = new byte[8192];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            long total = response.body().contentLength();
            if (listener != null) {
                listener.onStart(total);
            }
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;

                if (listener != null) {
                    listener.onProgress(sum);
                }
            }
            fos.flush();

            if (listener != null)
                listener.onDone(file);

        } catch (Exception e) {
            if (listener != null) listener.onFailure(e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
    }


    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    private static void onFail(ProgressListener listener, Throwable s) {
        if (listener != null) {
            listener.onFailure(s);
        }
    }


    private static void onFail(ProgressListener listener, String s) {
        if (listener != null) {
            listener.onFailure(new RuntimeException(s));
        }
    }


    /**
     * http调用
     *
     * @param url       地址
     * @param params    get请求时，参数为Map<String, String>，Post请求时，参数为json原对象
     * @param files     文件对象组
     * @param type      传入字符串 get 或者 post
     * @param async     是否异步
     * @param reqbejson 请求参数是否是json, 若是，那么params参数应该传对象
     * @param callback  请求回调
     * @param header    头部
     * @return
     */
    public static String httpReq(String url, Object params, Map<String, File[]> files, String type,
                                 boolean async, boolean reqbejson, Callback callback, Map<String, String> header,
                                 final ProgressListener listener) {
        String ret = null;
        OkHttpClient okHttpClient = getOkHttpClient();
        Request.Builder rb = new Request.Builder();
        if (header != null && !header.isEmpty()) {
            for (String key : header.keySet()) {
                rb.addHeader(key, header.get(key));
            }
        }
        if (url == null || url.isEmpty()) {
            onFail(listener, "url is empty.");
        }
        Request request;
        try {
            RequestBody requestBody = null;
            //确定调用方式
            if (type.equals("get")) {
                if (params != null) {
                    if (params instanceof Map) {
                        Map<String, String> pMap = (Map<String, String>) params;
                        StringBuffer sb = new StringBuffer();
                        if (!pMap.isEmpty()) {
                            for (String key : pMap.keySet()) {
                                sb.append(key + "=" + pMap.get(key) + "&");
                            }
                            if (!sb.toString().trim().equals("")) {
                                sb.deleteCharAt(sb.length() - 1);
                                if (url.contains("?")) {
                                    url += sb;
                                } else {
                                    url += "?" + sb;
                                }
                            }
                        }
                    } else {
                        onFail(listener, "param must be instance of Map<String, String>");
                    }
                }
            } else if (type.equals("post")) {

                RequestBody rb_param;
                if (reqbejson) {
                    String json = new Gson().toJson(params);   // 把对象转为JSON
                    rb_param = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                } else {
                    FormBody.Builder builder = new FormBody.Builder();
                    if (params != null) {
                        if (params instanceof Map) {
                            Map<String, String> pMap = (Map<String, String>) params;
                            // 遍历集合
                            if (!pMap.isEmpty()) {
                                for (String key : pMap.keySet()) {
                                    builder.add(key, pMap.get(key));
                                }
                            }
                        } else {
                            onFail(listener, "param must be instance of Map<String, String>");
                        }
                    }
                    rb_param = builder.build();
                }
                requestBody = rb_param;
                if (files != null) {
                    MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                    if (!files.isEmpty()) {
                        for (String key : files.keySet()) {
                            File[] fs = files.get(key);
                            for (File f : fs) {
                                //RequestBody fileBody = RequestBody.create(MutilPart_Form_Data, f);
                                RequestBody fileBody = buildFileRequestBody(f, listener);
                                multipartBodyBuilder.addFormDataPart(key, f.getName(), fileBody);
                            }
                        }
                    }
                    requestBody =  multipartBodyBuilder.addPart(rb_param).build();
                }
            } else {
                onFail(listener, "type must be get or post");
            }

            rb.url(url);
            if (requestBody != null) {
                rb.post(requestBody);
            }

            request = rb.build();

            Call call = okHttpClient.newCall(request);
            if (async) {
                if (callback == null) {
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call arg0, Response arg1) throws IOException {
                            String r = arg1.body().string();
                        }

                        @Override
                        public void onFailure(Call arg0, IOException arg1) {
                            arg1.printStackTrace();
                            onFail(listener, arg1);
                        }
                    });
                } else {
                    call.enqueue(callback);
                }
            } else {
                Response response = call.execute();
                ret = response.body().string();

                return ret;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
        return ret;
    }


    /**
     * 下载文件
     *
     * @param url      文件地址
     * @param file     文件保存目录
     * @param listener 文件下载信息监听
     */
    public static void download(final String url, final File file, final ProgressListener listener) {

        if (file == null) {
            if (listener != null) {
                listener.onFailure(new IOException("file is null"));
            }
            return;
        }

        httpReq(url, null, null, "get", true, false, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                File saveFile = null;
                if (file.isDirectory()) {
                    saveFile = new File(file.getPath(), getNameFromUrl(url));
                } else {
                    saveFile = file;
                }
                saveFile(saveFile, response, listener);
            }
        }, null, listener);
    }


}
