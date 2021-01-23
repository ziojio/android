package zhuj.jsbridge;

public interface OnCallJavaCallback<T> {
    void onCompleted(T retValue);
    void onProgress(T progress);
}
