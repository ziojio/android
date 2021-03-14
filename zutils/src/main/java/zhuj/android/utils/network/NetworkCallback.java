package zhuj.android.utils.network;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

import zhuj.android.utils.BuildConfig;


public class NetworkCallback extends ConnectivityManager.NetworkCallback {
    public static final String TAG = "NetworkCallback";
    public static final boolean debug = BuildConfig.DEBUG;

    protected boolean hasNetwork;

    public boolean isHasNetwork() {
        return hasNetwork;
    }

    public void setHasNetwork(boolean hasNetwork) {
        this.hasNetwork = hasNetwork;
    }

    /**
     * 网络可用的回调连接成功
     */
    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        hasNetwork = true;
        if(debug){
            Log.d(TAG, "onAvailable: " + network.toString());
        }
    }

    /**
     * 网络不可用时调用和onAvailable成对出现
     */
    @Override
    public void onLost(Network network) {
        super.onLost(network);
        hasNetwork = false;
        if(debug){
            Log.d(TAG, "onLost: " + network.toString());
        }
    }

    /**
     * 在网络连接正常的情况下，丢失数据会有回调 即将断开时
     */
    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        hasNetwork = false;
        if(debug){
            Log.d(TAG, "onLosing: " + network.toString() + ", maxMsToLive=" + maxMsToLive);
        }
    }

    /**
     * 网络功能更改 满足需求时调用
     *
     * @param network
     * @param networkCapabilities
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if(debug){
            Log.d(TAG, "onCapabilitiesChanged: " + network.toString() + ", " + networkCapabilities.toString());
        }
    }

    /**
     * 网络连接属性修改时调用
     *
     * @param network
     * @param linkProperties
     */
    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        if(debug){
            Log.d(TAG, "onLinkPropertiesChanged: " + network.toString() + ", " + linkProperties.toString());
        }
    }

    /**
     * 网络缺失network时调用
     */
    @Override
    public void onUnavailable() {
        super.onUnavailable();
        hasNetwork = false;
        if(debug){
            Log.d(TAG, "onUnavailable: ");
        }
    }

}
