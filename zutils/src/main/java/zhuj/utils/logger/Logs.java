package zhuj.utils.logger;

import android.os.Looper;

public class Logs {
    public static void isUiThread() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Logger.d("Current Thread is UI Thread " + Thread.currentThread().toString());
        } else {
            Logger.d("Current Thread is Work Thread " + Thread.currentThread().toString());
        }
    }
}
