package com.jbzh.android.util;

import android.os.Environment;
import android.view.MotionEvent;

import com.jbzh.android.bean.SymbolLinkInfo;
import com.jbzh.android.logger.Logger;
import com.jbzh.jbpaintviewcore.jbpaintcore.jbpaintunit;
import com.jbzh.jbpaintviewcore.jbpaintcore.jbpaintview;
import com.jbzh.jbsdk.oss.jbosssdk;

import java.io.File;

public class PaintUtil {
    public static final String[] linkName = new String[]{"link", "audio", "picture", "video"};
    private jbpaintview paintView;
    private String paintFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jbzh/paintview/file/";

    public PaintUtil(jbpaintview paintView) {
        this.paintView = paintView;
    }

    public void changePaintViewPoint(int x, int y, float scale) {
        paintView.Activesheet.resetviewpoint(x, y, scale);
    }

    public void onSymbolClick(jbpaintunit symbol, MotionEvent e, String taginfo) {
        SymbolLinkInfo info = GsonUtil.fromJson(taginfo, SymbolLinkInfo.class);
        if (info != null) {
            switch (info.getName()) {
                case "link":
                    break;
                case "audio":
                    new MediaPlayerUtil().play(info.getUrl());
                    break;
                case "picture":
                    break;
                case "video":
                    break;
                default:
                    Logger.w("onSymbolClick: 走到了 default 分支");
            }
        }
    }

    public void saveAsImage(String fileName, String fileType, boolean isSmall) {
        String pathName = paintFilePath + fileName;
        final String PNG = "png";
        final String JPG = "jpg";
        if (PNG.equalsIgnoreCase(fileType)) {
            if (isSmall) {
                paintView.Activesheet.exportpng2smallfile(pathName, 100);
            } else {
                paintView.Activesheet.exportpng2file(pathName, 100);
            }
        } else if (JPG.equalsIgnoreCase(fileType)) {
            if (isSmall) {
                paintView.Activesheet.exportjpg2smallfile(pathName, 100);
            } else {
                paintView.Activesheet.exportjpg2file(pathName, 100);
            }
        }
    }

    public boolean saveAsFile(String fileName) {
        File file = FileUtils.isFileNotExistCreate(fileName);
        if (file == null) {
            Logger.e("创建输出文件失败！");
            return false;
        }
        return paintView.SaveToBufferFile(file.getAbsolutePath());
    }

    public void saveFileAndUpload(String fileName, jbosssdk.uploadcallback uploadcallback) {
        String pathName = paintFilePath + fileName;
        if (saveAsFile(pathName)) {
            jbosssdk.asyncPutfile(fileName, pathName, uploadcallback);
            // 上传缩略图, 这个 callback 什么也不做
            saveAsImage(fileName + ".png", "png", true);
            jbosssdk.asyncPutfile(fileName + ".png", pathName + ".png", new jbosssdk.uploadcallback() {
                @Override
                public void onSuccess(String msg, String url) {

                }

                @Override
                public void onFailed(String msg) {

                }

                @Override
                public void onProgress(int progress, long maxsize) {

                }
            });
        } else {
            uploadcallback.onFailed("文件未保存成功！");
        }
    }

    public void readPaintFromFile(String fileName) {
        paintView.LoadfromFile(fileName);
    }

}
