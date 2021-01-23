package zhuj.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkChecker {

    public enum NetType {
        Wifi,
        Wired,
        Mobile,
        Mobile2G,
        Mobile3G,
        Mobile4G
    }

    private final ConnectivityManager mManager;

    public NetworkChecker(Context context) {
        this.mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Check the network is enable.
     */
    public boolean isAvailable() {
        return isWifiConnected() || isWiredConnected() || isMobileConnected();
    }

    /**
     * To determine whether a WiFi network is available.
     */
    public final boolean isWifiConnected() {
        return isAvailable(NetType.Wifi);
    }

    /**
     * To determine whether a wired network is available.
     */
    public final boolean isWiredConnected() {
        return isAvailable(NetType.Wired);
    }

    /**
     * Mobile Internet connection.
     */
    public final boolean isMobileConnected() {
        return isAvailable(NetType.Mobile);
    }

    /**
     * 2G Mobile Internet connection.
     */
    public final boolean isMobile2GConnected() {
        return isAvailable(NetType.Mobile2G);
    }

    /**
     * 3G Mobile Internet connection.
     */
    public final boolean isMobile3GConnected() {
        return isAvailable(NetType.Mobile3G);
    }

    /**
     * 4G Mobile Internet connection.
     */
    public final boolean isMobile4GConnected() {
        return isAvailable(NetType.Mobile4G);
    }

    /**
     * According to the different type of network to determine whether the network connection.
     */
    public final boolean isAvailable(NetType netType) {
        return isConnected(netType, mManager.getActiveNetworkInfo());
    }

    private static boolean isConnected(NetType netType, NetworkInfo networkInfo) {
        if (networkInfo == null) return false;
        switch (netType) {
            case Wifi: {
                if (!isConnected(networkInfo)) return false;
                return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            }
            case Wired: {
                if (!isConnected(networkInfo)) return false;
                return networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET;
            }
            case Mobile: {
                if (!isConnected(networkInfo)) return false;
                return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
            case Mobile2G: {
                if (!isConnected(NetType.Mobile, networkInfo)) return false;
                return isMobileSubType(NetType.Mobile2G, networkInfo);
            }
            case Mobile3G: {
                if (!isConnected(NetType.Mobile, networkInfo)) return false;
                return isMobileSubType(NetType.Mobile3G, networkInfo);
            }
            case Mobile4G: {
                if (!isConnected(NetType.Mobile, networkInfo)) return false;
                return isMobileSubType(NetType.Mobile4G, networkInfo);
            }
        }
        return false;
    }

    /**
     * Whether network connection.
     */
    private static boolean isConnected(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    private static boolean isMobileSubType(NetType netType, NetworkInfo networkInfo) {
        switch (networkInfo.getType()) {
            case TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: {
                return netType == NetType.Mobile2G;
            }
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP: {
                return netType == NetType.Mobile3G;
            }
            case TelephonyManager.NETWORK_TYPE_IWLAN:
            case TelephonyManager.NETWORK_TYPE_LTE: {
                return netType == NetType.Mobile4G;
            }
            default: {
                String subtypeName = networkInfo.getSubtypeName();
                if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                        || subtypeName.equalsIgnoreCase("WCDMA")
                        || subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return netType == NetType.Mobile3G;
                }
                break;
            }
        }
        return false;
    }
}