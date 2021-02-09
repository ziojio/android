package zhuj.android.network;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;


public class NetworkCallback extends ConnectivityManager.NetworkCallback {
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
    }

    /**
     * 网络不可用时调用和onAvailable成对出现
     */
    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        hasNetwork = false;
    }

    /**
     * 在网络连接正常的情况下，丢失数据会有回调 即将断开时
     */
    @Override
    public void onLosing(@NonNull Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        hasNetwork = false;
    }

    /**
     * 网络功能更改 满足需求时调用
     *
     * @param network
     * @param networkCapabilities
     */
    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    /**
     * 网络连接属性修改时调用
     *
     * @param network
     * @param linkProperties
     */
    @Override
    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
    }

    /**
     * 网络缺失network时调用
     */
    @Override
    public void onUnavailable() {
        super.onUnavailable();
        hasNetwork = false;
    }

}
