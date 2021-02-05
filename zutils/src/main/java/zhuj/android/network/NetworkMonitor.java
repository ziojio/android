package zhuj.android.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.telephony.TelephonyManager;

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
        register(app.getApplicationContext(), networkCallback);
    }

    public NetworkCallback getNetworkCallback() {
        return networkCallback;
    }

    /**
     * 初始化网络监听 根据不同版本做不同的处理
     */
    public static void register(Context context, ConnectivityManager.NetworkCallback networkCallback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            connectivityManager.registerNetworkCallback(request, networkCallback);
        } else {
            throw new IllegalStateException("No Handle Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
        }
    }

    public static void unregister(Context context, ConnectivityManager.NetworkCallback networkCallback) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        } else {
            throw new IllegalStateException("No Handle Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP");
        }
    }

    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        // 获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            // WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                netType = 3;

            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                //2G 移动和联通的2G为 GPRS或EGDE，电信的2G为CDMA
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }
}