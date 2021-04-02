package zhuj.android.utils;

import android.os.Environment;


public class StorageUtils {

    /**
     * SD卡是否可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


}
