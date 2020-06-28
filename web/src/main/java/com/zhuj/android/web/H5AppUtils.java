package com.zhuj.android.web;

import android.content.Context;
import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class H5AppUtils {
    private static final String TAG = "H5App";
    private static String appName = "h5app";
    private static String appHost = "http://h5app.jbzh.com";
    private static String version = "1.0.0";
    private static String pagePath = "";
    private static String params = "";

    private static String localH5AppDir = "";
    private static String cacheDir = "";

    private static boolean isInit = false;

    public static void init(Context ctx) {
        localH5AppDir = ctx.getFilesDir().getPath() + "/h5app";
        cacheDir = ctx.getCacheDir().getPath() + "/h5app";
        File dir = new File(localH5AppDir);
        if (!dir.exists())
            dir.mkdirs();
        File cache = new File(cacheDir);
        if (!cache.exists())
            cache.mkdirs();
        isInit = true;
    }

    public static void openH5App(@NonNull FragmentActivity activity, @NonNull String appName, String version, String pagePath) {
        final String jbapp_name = "hellouniapp";
        final String jbapp_path = "/pages/component/button/button";
        final String jbapp_version = "v1.0.2";
        String params = "?im_token=" + "";

        if (isExists(activity, appName, version)) {
            openInActivity(activity, appName, version, pagePath);
        } else {
            downloadToLocal(appName, version, new DownloadListener() {
                @Override
                public void onProgress(int progress) {

                }

                @Override
                public void onSuccess(File file) {
                    unzip(file, localH5AppDir);
                }

                @Override
                public void onFailure(IOException e) {

                }
            });
        }
    }

    private static void unzip(File file, String localH5AppDir) {

    }

    private static void downloadToLocal(String appName, String version, DownloadListener downloadListener) {
        // OkHttpClient okHttpClient = new OkHttpClient();
        // String url = getRemoteFileUrl(appName, version);
        // Request request = new Request.Builder().url(url).build();
        // Call call = okHttpClient.newCall(request);
        // call.enqueue(new Callback() {
        //     @Override
        //     public void onFailure(@NonNull Call call, @NonNull IOException e) {
        //         downloadListener.onFailure(e);
        //     }
        //
        //     @Override
        //     public void onResponse(@NonNull Call call, @NonNull Response response) {
        //         byte[] buf = new byte[2048];
        //         // 储存下载文件的目录
        //         File file = new File(cacheDir, url.substring(url.lastIndexOf('/')));
        //         try (InputStream is = response.body().byteStream(); FileOutputStream fos = new FileOutputStream(file)) {
        //             long total = response.body().contentLength();
        //             long sum = 0;
        //             int len = 0;
        //             while ((len = is.read(buf)) != -1) {
        //                 fos.write(buf, 0, len);
        //                 sum += len;
        //                 int progress = (int) (sum * 1.0f / total * 100);
        //                 downloadListener.onProgress(progress); // 下载中更新进度条
        //             }
        //             fos.flush();
        //             downloadListener.onSuccess(file); // 下载完成
        //         } catch (IOException e) {
        //             downloadListener.onFailure(e);
        //         }
        //     }
        // });
    }

    public static boolean isExists(@NonNull Context ctx, @NonNull String appName, String version) {
        if (!isInit) init(ctx);
        String appPath = getH5AppLocalFilePath(appName, version);
        return new File(appPath).exists();
    }

    public static String getH5AppLocalFilePath(String appName, String version) {
        return localH5AppDir + "/" + appName + "/" + version + "/index.html";
    }

    public static String getRemoteFileUrl(String appName, String version) {
        return appHost + "/zip/" + appName + "/" + appName + "_" + version + ".zip";
    }

    public static void openInFragment(@NonNull FragmentActivity activity, int containerViewId, String tag,
                                      @NonNull String appName, String version, String pagePath) {
        // jbh5AppFragment h5AppFragment = new jbh5AppFragment(appName, appHost, pagePath + params, version);
        // FragmentManager manager = activity.getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction = manager.beginTransaction();
        // fragmentTransaction.add(containerViewId, h5AppFragment, tag);
        // fragmentTransaction.commit();
    }

    public static void openInActivity(@NonNull FragmentActivity activity, @NonNull String appName, String version, String pagePath) {
        // Intent intent = new Intent(activity, jbh5appActivity.class);
        // intent.putExtra("jbappname", appName);
        // intent.putExtra("jbapphost", appHost);
        // intent.putExtra("jbapppath", pagePath);
        // intent.putExtra("jbappversion", version);
        // activity.startActivity(intent);
    }

    public interface DownloadListener {
        void onProgress(int progress);

        void onSuccess(File file);

        void onFailure(IOException e);
    }
}