package zhuj.android.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import zhuj.android.utils.BuildConfig;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    private ScreenStateListener screenStateListener;

    public interface ScreenStateListener {

        void onScreenOff();

        void onScreenOn();

        void onUserPresent();

    }

    public ScreenBroadcastReceiver() {
    }

    public ScreenBroadcastReceiver(ScreenStateListener screenStateListener) {
        this.screenStateListener = screenStateListener;
    }

    public void setScreenStateListener(ScreenStateListener screenStateListener) {
        this.screenStateListener = screenStateListener;
    }

    public ScreenStateListener getScreenStateListener() {
        return screenStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (screenStateListener == null) {
            return;
        }
        String action = intent.getAction();
        if (DEBUG) {
            Log.d("ScreenBroadcastReceiver", "Action=" + action);
        }
        if (action == null) {
            return;
        }
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            screenStateListener.onScreenOff();
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            screenStateListener.onScreenOn();
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            screenStateListener.onUserPresent();
        }
    }
}
