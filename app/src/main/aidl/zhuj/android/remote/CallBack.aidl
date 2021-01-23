package zhuj.android.remote;

// Declare any non-default types here with import statements
import android.content.Intent;

interface CallBack {
    void onResult(int flag, out Intent intent);
}
