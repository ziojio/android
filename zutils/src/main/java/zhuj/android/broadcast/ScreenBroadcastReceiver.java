package zhuj.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import zhuj.android.logger.Logger;

public class ScreenBroadcastReceiver extends BroadcastReceiver {
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
        Logger.d("ScreenBroadcastReceiver", "Action=" + action);

        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            screenStateListener.onScreenOff();
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            screenStateListener.onScreenOn();
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            screenStateListener.onUserPresent();
        }
    }
}
