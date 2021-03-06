package zhuj.android.utils.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import zhuj.android.utils.ResUtils;
import zhuj.java.file.IOUtils;
import zhuj.java.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class AssetstUtils {

    private static final String LINE_BREAK = "\r\n";

    private AssetstUtils() {
    }

    /**
     * 读取Assets下的txt文件
     *
     * @param fileName 文件名
     * @return 文本内容
     */
    public static String readStringFromAssets(Context context, String fileName) {
        return readStringFromAssets(context, fileName, "utf-8");
    }

    /**
     * 读取Assets下的txt文件
     *
     * @param fileName     文件名
     * @param encodingCode 字符编码
     * @return 文本内容
     */
    public static String readStringFromAssets(Context context, String fileName, String encodingCode) {
        InputStream inputStream = null;
        try {
            inputStream = openAssetsFile(context, fileName);
            if (inputStream != null) {
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                return new String(buffer, encodingCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(inputStream);
        }
        return "";
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     */
    public static InputStream openAssetsFile(Context context, String fileName) {
        try {
            return openAssetsFileWithException(context, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     * @throws IOException
     */
    public static InputStream openAssetsFileWithException(Context context, String fileName) throws IOException {
        return getAssetManager(context).open(fileName);
    }

    /**
     * 打开Raw下的资源
     *
     * @param resId
     * @return
     */
    public static InputStream openRawResource(Context context, int resId) {
        return context.getResources().openRawResource(resId);
    }

    /**
     * 获取AssetManager
     *
     * @return
     */
    public static AssetManager getAssetManager(Context context) {
        return context.getResources().getAssets();
    }

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName) {
        return getFileFromAssets(context, fileName, true);
    }

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName      文件名
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName, boolean isNeedAddLine) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }
        return readInputStream(openAssetsFile(context, fileName), isNeedAddLine);
    }


    /**
     * 读取raw下文件的内容
     *
     * @param resId 文件资源id
     * @return
     */
    public static String getFileFromRaw(Context context, int resId) {
        return getFileFromRaw(context, resId, true);
    }

    /**
     * 读取raw下文件的内容
     *
     * @param resId         文件资源id
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String getFileFromRaw(Context context, int resId, boolean isNeedAddLine) {
        return readInputStream(openRawResource(context, resId), isNeedAddLine);
    }

    /**
     * 读取输入流
     *
     * @param inputStream   输入流
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String readInputStream(InputStream inputStream, boolean isNeedAddLine) {
        StringBuilder s = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            if (isNeedAddLine) {
                while ((line = br.readLine()) != null) {
                    s.append(line).append(LINE_BREAK);
                }
            } else {
                while ((line = br.readLine()) != null) {
                    s.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(br);
        }
        return s.toString();
    }

    /**
     * 从Assets中读取图片
     *
     * @param fileName 文件名
     */
    @Nullable
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        InputStream is = null;
        AssetManager am = getAssetManager(context);
        try {
            is = am.open(fileName);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
        }
        return null;
    }

    /**
     * 从Assets中读取图片
     *
     * @param fileName 文件名
     */
    @Nullable
    public static Bitmap getImageFromAssets(Context context, String fileName) {
        InputStream is = null;
        try {
            is = openAssetsFileWithException(context, ResUtils.DRAWABLE + "/" + fileName);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(is);
        }
        return null;
    }

    // /**
    //  * 从Assets中读取图片
    //  */
    // @Nullable
    // public static Drawable getImageDrawableFromAssets(String fileName) {
    //     InputStream is = null;
    //     try {
    //         is = openAssetsFileWithException(Resources.DRAWABLE + "/" + fileName);
    //         return ImageUtils.bitmap2Drawable(BitmapFactory.decodeStream(is));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } finally {
    //         IOUtils.close(is);
    //     }
    //     return null;
    // }
    //
    // /**
    //  * 从assets目录中复制整个文件夹内容
    //  *
    //  * @param oldPath 原文件路径  如：/aa
    //  * @param newPath 复制后路径  如：xx:/bb/cc
    //  */
    // public static void copyFilesFromAssets(String oldPath, String newPath) {
    //     try {
    //         // 获取assets目录下的所有文件及目录名
    //         String[] fileNames = getAssetManager().list(oldPath);
    //         // 如果是目录
    //         if (fileNames != null && fileNames.length > 0) {
    //             if (FileUtils.createOrExistsDir(newPath)) {
    //                 for (String fileName : fileNames) {
    //                     copyFilesFromAssets(oldPath + File.separator + fileName, newPath + File.separator + fileName);
    //                 }
    //             }
    //         } else {// 如果是文件
    //             FileIOUtils.writeFileFromIS(newPath, getAssetManager().open(oldPath));
    //         }
    //     } catch (Exception e) {
    //         Logger.e(e);
    //     }
    // }
    //
    // /**
    //  * 从assets目录中复制指定文件至指定目录下
    //  *
    //  * @param fileName 需要复制的文件名
    //  * @param srcDir   文件在assets下的目录
    //  * @param destDir  复制后的目录
    //  */
    // public static boolean copyFileFromAssets(String fileName, String srcDir, String destDir) {
    //     try {
    //         if (FileUtils.createOrExistsDir(destDir)) {
    //             return FileIOUtils.writeFileFromIS(destDir + File.separator + fileName, getAssetManager().open(srcDir + File.separator + fileName));
    //         }
    //     } catch (Exception e) {
    //         Logger.e(e);
    //     }
    //     return false;
    // }
    //
    // /**
    //  * 从assets目录中复制指定文件至指定目录下
    //  *
    //  * @param fileName     需要复制的文件名
    //  * @param assetsSrcDir 文件在assets下的目录
    //  * @param destDir      复制后的目录
    //  * @return 复制后的文件路径
    //  */
    // public static String getCopyFileFromAssets(String fileName, String assetsSrcDir, String destDir) {
    //     try {
    //         if (FileUtils.createOrExistsDir(destDir)) {
    //             String copyFilePath = destDir + File.separator + fileName;
    //             if (FileIOUtils.writeFileFromIS(copyFilePath, getAssetManager().open(assetsSrcDir + File.separator + fileName))) {
    //                 return copyFilePath;
    //             }
    //         }
    //     } catch (Exception e) {
    //         Logger.e(e);
    //     }
    //     return EMPTY;
    // }

}
