package com.zhuj.android.app.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.zhuj.android.util.permission.easypermission.GrantResult;
import com.zhuj.android.util.permission.easypermission.Permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PermissionRequestFragment extends Fragment implements Runnable {
    private static final String TAG = PermissionRequestFragment.class.getSimpleName();
    private int mRequestCode;
    private RequestCodeGenerater mRequestCodeGenerater = new RequestCodeGenerater();
    private HashMap<String, GrantResult> mPermissionGrantMap = new HashMap<>();

    public static PermissionRequestFragment build(HashMap<String, GrantResult> permissionMap) {
        PermissionRequestFragment fragment = new PermissionRequestFragment();
        fragment.setPermissionGrantMap(permissionMap);
        return fragment;
    }

    public void setPermissionGrantMap(HashMap<String, GrantResult> permissionGrantMap) {
        mPermissionGrantMap = permissionGrantMap;
    }

    public void go(FragmentActivity activity) {
        if (activity != null) {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new RuntimeException("you must request permission in main thread!!");
            }
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(this, activity.getClass().getName())
                    .commit();
        } else {
            throw new RuntimeException("activity is null!!");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRequestCode = mRequestCodeGenerater.generate();
        if ((mPermissionGrantMap.containsKey(Permission.REQUEST_INSTALL_PACKAGES)
                && mPermissionGrantMap.get(Permission.REQUEST_INSTALL_PACKAGES) == GrantResult.DENIED)
                || (mPermissionGrantMap.containsKey(Permission.SYSTEM_ALERT_WINDOW)
                && mPermissionGrantMap.get(Permission.SYSTEM_ALERT_WINDOW) == GrantResult.DENIED)) {
            if (mPermissionGrantMap.containsKey(Permission.REQUEST_INSTALL_PACKAGES)) {
                //跳转到允许安装未知来源设置页面
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, mRequestCode);
            }

            if (mPermissionGrantMap.containsKey(Permission.SYSTEM_ALERT_WINDOW)) {
                //跳转到悬浮窗设置页面
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, mRequestCode);
            }

        } else {
            startRequestPermission();
        }
    }

    private void startRequestPermission() {
        ArrayList<String> needRequestPermissionList = new ArrayList<>();
        Set<Map.Entry<String, GrantResult>> entrySet = mPermissionGrantMap.entrySet();
        for (Map.Entry<String, GrantResult> entry : entrySet) {
            if (entry.getValue() == GrantResult.DENIED) {
                needRequestPermissionList.add(entry.getKey());
            }
        }

        for (String permission : needRequestPermissionList) {
            Log.i(TAG, "需要申请的权限：" + permission);
        }
        if (needRequestPermissionList.isEmpty()) {
            Log.i(TAG, "没有需要申请的权限，直接回调");
            return;
        }
        String[] permissionArray = needRequestPermissionList.toArray(new String[0]);
        if (permissionArray.length <= 0) {
            return;
        }
        requestPermissions(permissionArray, mRequestCode);
    }

    private boolean isBackCall; // 是否已经回调了，避免安装权限和悬浮窗同时请求导致的重复回调

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode + ", data=" + data);

        if (!isBackCall && requestCode == mRequestCode) {
            isBackCall = true;
            //需要延迟执行，不然有些华为机型授权了但是获取不到权限
            getActivity().getWindow().getDecorView().postDelayed(this, 500);
        }
    }

    @Override
    public void run() {
        //请求其他危险权限
        startRequestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != mRequestCode) {
            Log.i(TAG, "onRequestPermissionsResult: requestCode不一致，不处理");
            return;
        }

        for (int i = 0; i < permissions.length; i++) {
            Log.i(TAG, "onRequestPermissionsResult: 权限：" + permissions[i] + ", 是否授权：" + grantResults[i]);

            mPermissionGrantMap.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED ? GrantResult.GRANT : GrantResult.DENIED);

        }

        //打印返回结果
        Set<Map.Entry<String, GrantResult>> entrySet = mPermissionGrantMap.entrySet();
        Log.i(TAG, "打印最终返回结果：");
        for (Map.Entry<String, GrantResult> entry : entrySet) {
            Log.i(TAG, "权限：" + entry.getKey() + ", 状态：" + entry.getValue());
        }
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }

    /**
     * requestCode的生成器，用于生成不重复的requestCode
     */
    private static class RequestCodeGenerater {
        /**
         * 生成初始化值
         */
        private volatile static int FACTOR_REQUEST_CODE = 0;

        /**
         * 同步生成方法
         */
        public synchronized int generate() {
            return FACTOR_REQUEST_CODE++;
        }
    }

}
