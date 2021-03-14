package zhuj.android.utils.network;

import android.content.Context;

public class NetworkMonitor {

    private static final NetworkMonitor Instance = new NetworkMonitor();

    public static NetworkMonitor getInstance() {
        return Instance;
    }

    private NetworkCallback networkCallback;

    private NetworkMonitor() {
    }

    public void registerForApp(Context app) {
        if (networkCallback != null) {
            return;
        }
        networkCallback = new NetworkCallback();
        NetworkUtils.register(app.getApplicationContext(), networkCallback);
    }

    public NetworkCallback getNetworkCallback() {
        return networkCallback;
    }

}