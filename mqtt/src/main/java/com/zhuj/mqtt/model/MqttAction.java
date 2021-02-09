package zhuj.mqtt.model;

public enum MqttAction {
    /**
     * 连接动作
     **/
    CONNECT("连接"),
    /**
     * 断开动作
     **/
    DISCONNECT("断开连接"),
    /**
     * 订阅动作
     **/
    SUBSCRIBE("订阅"),
    /**
     * 取消订阅动作
     **/
    UNSUBSCRIBE("取消订阅"),
    /**
     * 发送动作
     **/
    PUBLISH("发送");

    /**
     * 名称
     */
    public String actionName;
    /**
     * 携带的参数
     */
    public Object args;

    MqttAction(String actionName) {
        this.actionName = actionName;
    }

    public String getName() {
        return actionName;
    }

    public MqttAction setArgs(Object args) {
        this.args = args;
        return this;
    }

    public Object getArgs() {
        return args;
    }
}
