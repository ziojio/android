package zhuj.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageStateReceiver extends BroadcastReceiver {
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
        String type = intent.getAction().substring(22).toUpperCase();
        String p = intent.getDataString().substring(8);
        if (type.equals("PACKAGE_ADDED")) {
            packageStateListener.onAdded(p);
        } else if (type.equals("PACKAGE_REMOVED")) {
            packageStateListener.onRemoved(p);
        } else if (type.equals("PACKAGE_REPLACED")) {
            packageStateListener.onReplaced(p);
        }
    }
}