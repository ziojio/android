package com.zhuj.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    private ImageUtils(){
    }

    /**
     * 加载图片文件到内存
     *
     * @param filePath 文件路径
     * @param scale    是否根据屏幕的像素缩放加载
     * @return
     */
    public static Bitmap getBitmap(String filePath, boolean scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            options.inJustDecodeBounds = true; // 不加载到内存中
            InputStream input = new FileInputStream(filePath);
            BitmapFactory.decodeStream(input, null, options); // 此时返回bm为空
            options.inJustDecodeBounds = false;
            input.close();
        } catch (IOException io) {
            Log.e(TAG, "getBitmap: IOException=" + io.getCause());
            return null;
        }
        int originalWidth = options.outWidth;
        int originalHeight = options.outHeight;
        if (originalWidth == -1 || originalHeight == -1) {
            Log.e(TAG, "getBitmap: 解码失败 options.outWidth == -1 || outWidth == -1");
            return null;
        }
        int sampleSize = 1;
        // 要获取屏幕的大小
        float width = 1920f; // 缩放到屏幕大小，根据宽度
        float height = 1200f; // 保存到内存中，如果大于屏幕尺寸就缩放
        if (scale) {
            // 缩放比, 由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            if (originalWidth > originalHeight && originalWidth > width) {
                // 如果宽度大的话根据宽度缩放
                sampleSize = (int) (originalWidth / width);
            } else if (originalWidth < originalHeight && originalHeight > height) {
                // 如果高度高的话根据高度缩放
                sampleSize = (int) (originalHeight / height);
            }
        }
        options.inSampleSize = sampleSize;
        options.inDither = true;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 图片质量压缩
     * 压缩到指定的字节数，压缩质量要 > 30, 否则不再压缩
     *
     * @param bitmap
     * @param bytes
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int bytes) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        while (byteArrayOutputStream.toByteArray().length > bytes && quality > 30) {
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            quality -= 10;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return BitmapFactory.decodeStream(byteArrayInputStream, null, null);
    }

    /**
     * 缩放图片到指定大小
     *
     * @param bitmap
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bitmap, float newWidth, float newHeight) {
        // 获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算宽高缩放率
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

}
