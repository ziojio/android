package zhuj.jsbridge;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

class JavaScriptBridge {
    private String data;
    private int callbackId;
    private String method;

    JavaScriptBridge(String handlerName, int id, Object[] args) {
        if (args == null) args = new Object[0];
        data = new JSONArray(Arrays.asList(args)).toString();
        callbackId = id;
        method = handlerName;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("method", method);
            jo.put("callbackId", callbackId);
            jo.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }
}