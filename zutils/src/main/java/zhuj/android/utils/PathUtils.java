package zhuj.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;

public class PathUtils {

    public static final String TAG = "File Storage";

    public static Context getContext() {
        return Androids.getApp();
    }

    public static String getAuthority() {
        return getContext().getPackageName() + ".fileprovider";
    }


    /**
     * Return a content URI for a given file.
     *
     * @param file The file.
     * @return a content URI for a given file
     */
    @Nullable
    public static Uri getUriForFile(final File file) {
        if (file == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(getContext(), getAuthority(), file);
        } else {
            return Uri.fromFile(file);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri getDownloadContentUri(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Uri baseUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(baseUri,
                new String[]{MediaStore.Downloads._ID}, MediaStore.Downloads.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.DownloadColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (file.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DATA, filePath);
                return context.getContentResolver().insert(baseUri, values);
            }
            return null;
        }
    }
    /**
     * 将媒体文件转化为资源定位符
     *
     * @param mediaFile 媒体文件
     * @return
     */
    public static Uri getMediaImageContentUri(File mediaFile) {
        String filePath = mediaFile.getAbsolutePath();
        Uri baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContext().getContentResolver().query(baseUri,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.DownloadColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (mediaFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContext().getContentResolver().insert(baseUri, values);
            }
            return null;
        }
    }

    private static String getFilePath(File file) {
        return file != null ? file.getAbsolutePath() : "";
    }
    //===============================内置私有存储空间===========================================//

    /**
     * 获取此应用的缓存目录
     * <pre>path: /data/data/package/cache</pre>
     *
     * @return 此应用的缓存目录
     */
    public static String getAppCachePath() {
        return getContext().getCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用的文件目录
     * <pre>path: /data/data/package/files</pre>
     *
     * @return 此应用的文件目录
     */
    public static String getAppFilesPath() {
        return getContext().getFilesDir().getAbsolutePath();
    }

    /**
     * 获取此应用的数据库文件目录
     * <pre>path: /data/data/package/databases/name</pre>
     *
     * @param name 数据库文件名
     * @return 数据库文件目录
     */
    public static String getAppDbPath(String name) {
        return getContext().getDatabasePath(name).getAbsolutePath();
    }

    //===============================外置内部存储空间，这部分不需要获取读取权限===========================================//

    /**
     * 获取此应用在外置储存中的缓存目录
     * <pre>path: /storage/emulated/0/Android/data/package/cache</pre>
     *
     * @return 此应用在外置储存中的缓存目录
     */
    public static String getAppExtCachePath() {
        return getFilePath(getContext().getExternalCacheDir());
    }

    /**
     * 获取此应用在外置储存中的文件目录
     * <pre>path: /storage/emulated/0/Android/data/package/files</pre>
     *
     * @return 此应用在外置储存中的文件目录
     */
    public static String getAppExtFilePath() {
        return getFilePath(getContext().getExternalFilesDir(null));
    }

    /**
     * 获取此应用的 Obb 目录
     * <pre>path: /storage/emulated/0/Android/obb/package</pre>
     * <pre>一般用来存放游戏数据包</pre>
     *
     * @return 此应用的 Obb 目录
     */
    public static String getObbPath() {
        return getContext().getObbDir().getAbsolutePath();
    }


    /**
     * 获取 Android 外置储存的根目录
     * <pre>path: /storage/emulated/0</pre>
     *
     * @return 外置储存根目录
     */
    public static String getExtStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取相机拍摄的照片和视频的目录
     * <pre>path: /storage/emulated/0/DCIM</pre>
     *
     * @return 照片和视频目录
     */
    public static String getExtDCIMPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath();
    }

    /**
     * 获取文档目录
     * <pre>path: /storage/emulated/0/Documents</pre>
     *
     * @return 文档目录
     */
    public static String getExtDocumentsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath();
    }

    /**
     * 获取下载目录
     * <pre>path: /storage/emulated/0/Download</pre>
     *
     * @return 下载目录
     */
    public static String getExtDownloadsPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    /**
     * 获取视频目录
     * <pre>path: /storage/emulated/0/Movies</pre>
     *
     * @return 视频目录
     */
    public static String getExtMoviesPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                .getAbsolutePath();
    }

    /**
     * 获取音乐目录
     * <pre>path: /storage/emulated/0/Music</pre>
     *
     * @return 音乐目录
     */
    public static String getExtMusicPath() {
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath();
    }

}
