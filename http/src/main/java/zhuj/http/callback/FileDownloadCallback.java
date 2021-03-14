package zhuj.http.callback;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import zhuj.java.file.IOUtils;
import zhuj.http.util.ZDebug;

public abstract class FileDownloadCallback implements Callback {
    private static final String TAG = "FileDownloadCallback";

    private final File destFile;

    public FileDownloadCallback(File destFile) {
        this.destFile = destFile;
    }

    public FileDownloadCallback(String pathname) {
        this.destFile = new File(pathname);
    }

    public FileDownloadCallback(String dir, String filename) {
        this.destFile = new File(dir, filename);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        ZDebug.debug(TAG, "Call: " + call.request().url() + ", msg: " + e.getMessage());
    }

    public void onProgress(long size, long total, long speedB) {
    }

    public abstract void onSuccess(File file);

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            if (destFile.getParentFile() == null) {
                onFailure(call, new IOException("dest dir is null, valid path"));
            }
            if (!destFile.getParentFile().exists()) {
                destFile.mkdirs();
            }
            fos = new FileOutputStream(destFile);

            ResponseBody body = response.body();
            is = body.byteStream();
            long total = body.contentLength();
            long lastSize = 0;
            long lastTime = System.currentTimeMillis();
            long downloadSpeed = 0;

            onProgress(0, total, 0);

            int size = 0;
            int len;
            byte[] buf;
            if (total > 10240000) {
                buf = new byte[10240];
            } else {
                buf = new byte[1024];
            }
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                size += len;
                long cur = System.currentTimeMillis();
                if (cur - lastTime > 1000) {
                    downloadSpeed = size - lastSize;
                    lastSize = size;
                    lastTime = cur;
                }
                onProgress(size, total, downloadSpeed);
            }
            fos.flush();
            onSuccess(destFile);
        } catch (IOException e) {
            onFailure(call, e);
        } finally {
            IOUtils.close(is, fos);
        }
    }
}
