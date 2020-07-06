/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.zhuj.mqtt.listener;

import com.zhuj.mqtt.model.MqttAction;

import org.eclipse.paho.client.mqttv3.IMqttToken;

/**
 * MQTT 动作监听
 *
 * @author xuexiang
 * @since 2019-12-13 9:16
 */
public interface OnMqttActionListener {

    /**
     * 动作成功
     *
     * @param action      动作类型
     * @param actionToken
     */
    void onActionSuccess(MqttAction action, IMqttToken actionToken);

    /**
     * 动作失败
     *
     * @param action      动作类型
     * @param actionToken
     * @param exception
     */
    void onActionFailure(MqttAction action, IMqttToken actionToken, Throwable exception);

}