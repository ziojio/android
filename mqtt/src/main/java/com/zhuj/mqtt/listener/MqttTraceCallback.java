package zhuj.mqtt.listener;

import android.util.Log;

import org.eclipse.paho.android.service.MqttTraceHandler;

public class MqttTraceCallback implements MqttTraceHandler {

    private static final String TAG = "MQTT-";

    @Override
    public void traceDebug(String tag, String message) {
        Log.d(TAG + tag + ": ", message);
    }

    @Override
    public void traceError(String tag, String message) {
        Log.e(TAG + tag + ": ", message);
    }

    @Override
    public void traceException(String tag, String message, Exception e) {
        Log.d(TAG + tag, "traceException: " + message, e);
    }

}