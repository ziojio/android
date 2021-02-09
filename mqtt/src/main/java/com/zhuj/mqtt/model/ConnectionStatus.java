package zhuj.mqtt.model;

public enum ConnectionStatus {
    /**
     * 状态未知
     **/
    INITIAL,
    /**
     * 连接中
     **/
    CONNECTING,
    /**
     * 已连接
     **/
    CONNECTED,
    /**
     * 连接断开中
     **/
    DISCONNECTING,
    /**
     * 已断开连接
     **/
    DISCONNECTED,
    /**
     * 出错
     **/
    ERROR,
    /**
     * 出错
     **/
    UNKNOWN,
}