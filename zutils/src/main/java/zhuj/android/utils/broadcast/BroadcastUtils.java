package zhuj.android.utils.broadcast;

import android.content.Context;
import android.content.IntentFilter;

public class BroadcastUtils {


    public static void registerReceiver(Context context, PackageStateReceiver packageStateReceiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction("android.intent.action.PACKAGE_REPLACED");
        filter.addDataScheme("package");
        context.registerReceiver(packageStateReceiver, filter);
    }

}
