package zhuj.http.callback;

public interface ProgressListener<T> {
    int onProgress(int progress);

    void onSuccess(T t);

    void onFailure(Exception error);
}
