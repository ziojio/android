package zhuj.android.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import zhuj.android.utils.BuildConfig;

public class PackageStateReceiver extends BroadcastReceiver {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    private PackageStateListener packageStateListener;

    public interface PackageStateListener {

        void onAdded(String packageName);
        void onRemoved(String packageName);
        void onReplaced(String packageName);

    }

    public PackageStateReceiver() {
    }

    public PackageStateReceiver(PackageStateListener packageStateListener) {
        this.packageStateListener = packageStateListener;
    }

    public void setPackageStateListener(PackageStateListener packageStateListener) {
        this.packageStateListener = packageStateListener;
    }

    public PackageStateListener getPackageStateListener() {
        return packageStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (packageStateListener == null) {
            return;
        }
        String action = intent.getAction();
        if (DEBUG) {
            Log.d("ScreenBroadcastReceiver", "Action=" + action);
        }
        if (action == null || action.length() < 22) {
            return;
        }
        String type = action.substring(22).toUpperCase();
        String pkname = intent.getDataString();
        if (pkname == null || pkname.length() < 8) {
            return;
        }
        String p = pkname.substring(8);
        if (type.equals("PACKAGE_ADDED")) {
            packageStateListener.onAdded(p);
        } else if (type.equals("PACKAGE_REMOVED")) {
            packageStateListener.onRemoved(p);
        } else if (type.equals("PACKAGE_REPLACED")) {
            packageStateListener.onReplaced(p);
        }
    }
}