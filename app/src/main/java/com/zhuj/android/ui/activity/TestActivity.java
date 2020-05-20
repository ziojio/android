package com.zhuj.android.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.zhuj.android.R;
import com.zhuj.android.model.UpdateAppInfo;

import java.util.ArrayList;

public class TestActivity extends BaseActivity {
    ArrayList<UpdateAppInfo> downloadApkList = new ArrayList<>();
    ArrayList<String> strings = new ArrayList<>();

    UpdateAppInfo curUpdateAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        downloadApkList.add(new UpdateAppInfo("http://jbpp.vanpin.com/apk/jbim/v1.0.1/jbim.apk"));
        downloadApkList.add(new UpdateAppInfo("http://jbpp.vanpin.com/apk/jbim/v1.0.1/jbim2.apk"));
        downloadApkList.add(new UpdateAppInfo("http://jbpp.vanpin.com/apk/jbim/v1.0.1/jbim3.apk"));
        downloadApkList.add(new UpdateAppInfo("http://jbpp.vanpin.com/apk/jbim/v1.0.1/jbim4.apk"));
        downloadApkList.add(new UpdateAppInfo("http://jbpp.vanpin.com/apk/jbim/v1.0.1/jbim5.apk"));

        strings.add("jbim0.apk");
        strings.add("jbim1.apk");
        strings.add("jbim2.apk");
        strings.add("jbim3.apk");
        strings.add("jbim4.apk");
        strings.add("jbim5.apk");

        curUpdateAppInfo = downloadApkList.get(0);
        Log.d(TAG, "curUpdateAppInfo=" + curUpdateAppInfo);

        func1(downloadApkList);

        Log.d(TAG, "curUpdateAppInfo=" + curUpdateAppInfo);
    }

    private void func1(ArrayList<UpdateAppInfo> appInfo) {
        for (UpdateAppInfo appInfo1 : appInfo) {
            if (curUpdateAppInfo == appInfo1){
            }
        }
    }
}
