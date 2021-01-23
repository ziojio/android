package zhuj.android.app.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import zhuj.utils.permission.easypermission.GrantResult;

import java.util.HashMap;

public class TestFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<String, GrantResult> grantResultHashMap = new HashMap<>();
        grantResultHashMap.put(Manifest.permission.INTERNET, GrantResult.DENIED);
        grantResultHashMap.put(Manifest.permission.CAMERA, GrantResult.DENIED);
        grantResultHashMap.put(Manifest.permission.RECORD_AUDIO, GrantResult.DENIED);
        grantResultHashMap.put(Manifest.permission.ACCESS_WIFI_STATE, GrantResult.DENIED);
        grantResultHashMap.put(Manifest.permission.REQUEST_INSTALL_PACKAGES, GrantResult.DENIED);
        PermissionRequestFragment.build(grantResultHashMap).go(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(  "TestFragmentActivity"  , "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);
    }
}