package zhuj.android.utils.upload;

import java.util.HashMap;

import zhuj.java.function.FunctionI;

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();

    public static final int STATE_UPLOAD_NONE = 0;
    public static final int STATE_UPLOADING = 1;
    public static final int STATE_UPLOAD_SUCCESS = 2;
    public static final int STATE_UPLOAD_ERROR = 3;

    // local path, upload state
    HashMap<String, Integer> mUploadStateMap = new HashMap<>();
    // local path, upload result
    HashMap<String, String> mUploadMap = new HashMap<>();

    public void uploadFile(String path) {
        uploadFile(path, null);
    }

    public void uploadFile(String path, FunctionI<String> resultListener) {

    }

    void check(String path) {
        if (!mUploadMap.containsKey(path)) {
            throw new IllegalArgumentException("no contains " + path);
        }
    }

    String getResult(String path) {
        check(path);
        return mUploadMap.get(path);
    }

    int getState(String path) {
        check(path);
        Integer i = mUploadStateMap.get(path);
        return i == null ? -1 : i;
    }

}
