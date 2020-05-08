package com.zhuj.android.web;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class H5App {
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
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = getRemoteFileUrl(appName, version);
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                downloadListener.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                byte[] buf = new byte[2048];
                // 储存下载文件的目录
                File file = new File(cacheDir, url.substring(url.lastIndexOf('/')));
                try (InputStream is = response.body().byteStream(); FileOutputStream fos = new FileOutputStream(file)) {
                    long total = response.body().contentLength();
                    long sum = 0;
                    int len = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        downloadListener.onProgress(progress); // 下载中更新进度条
                    }
                    fos.flush();
                    downloadListener.onSuccess(file); // 下载完成
                } catch (IOException e) {
                    downloadListener.onFailure(e);
                }
            }
        });
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
class ZipUtils {
    private static final String TAG = "ZipUtils";
    private ZipUtils() {
    }

    /**
     * 解压zip到指定的路径
     *
     * @param zipFileString ZIP的名称
     * @param outPathString 要解压缩路径
     * @throws Exception
     */
    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                Log.e(TAG, outPathString + File.separator + szName);
                File file = new File(outPathString + File.separator + szName);
                if (!file.exists()) {
                    Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    public static void UnZipFolder(String zipFileString, String outPathString, String szName) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            //szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                //获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {
                Log.e(TAG, outPathString + File.separator + szName);
                File file = new File(outPathString + File.separator + szName);
                if (!file.exists()) {
                    Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                // 获取文件的输出流
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // 读取（字节）字节到缓冲区
                while ((len = inZip.read(buffer)) != -1) {
                    // 从缓冲区（0）位置写入（字节）字节
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 解压完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
        //创建ZIP
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        //创建文件
        File file = new File(srcFileString);
        //压缩
        Log.e(TAG,"---->"+file.getParent()+"==="+file.getAbsolutePath());
        ZipFiles(file.getParent()+ File.separator, file.getName(), outZip);
        //完成和关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        Log.e(TAG,"folderString:" + folderString + "\n" +
                "fileString:" + fileString + "\n==========================");
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            //文件夹
            String fileList[] = file.list();
            //没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            //子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString+fileString+"/",  fileList[i], zipOutputSteam);
            }
        }
    }

    /**
     * 返回zip的文件输入流
     *
     * @param zipFileString zip的名称
     * @param fileString    ZIP的文件名
     * @return InputStream
     * @throws Exception
     */
    public static InputStream UpZip(String zipFileString, String fileString) throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }

    /**
     * 返回ZIP中的文件列表（文件和文件夹）
     *
     * @param zipFileString  ZIP的名称
     * @param bContainFolder 是否包含文件夹
     * @param bContainFile   是否包含文件
     * @return
     * @throws Exception
     */
    public static List<File> GetFileList(String zipFileString, boolean bContainFolder, boolean bContainFile) throws Exception {
        List<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // 获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (bContainFolder) {
                    fileList.add(folder);
                }
            } else {
                File file = new File(szName);
                if (bContainFile) {
                    fileList.add(file);
                }
            }
        }
        inZip.close();
        return fileList;
    }
}
