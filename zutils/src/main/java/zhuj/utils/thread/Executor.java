package zhuj.utils.thread;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public class Executor {
    private static final java.util.concurrent.Executor MAIN_THREAD_EXECUTOR =
            new java.util.concurrent.Executor() {
                private final Handler handler = new Handler(Looper.getMainLooper());

                @Override
                public void execute(@NonNull Runnable command) {
                    handler.post(command);
                }
            };
    private static final java.util.concurrent.Executor DIRECT_EXECUTOR =
            new java.util.concurrent.Executor() {
                @Override
                public void execute(@NonNull Runnable command) {
                    command.run();
                }
            };
}
