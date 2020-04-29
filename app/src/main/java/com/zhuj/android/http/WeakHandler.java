package com.zhuj.android.http;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.zhuj.android.ui.activity.MainActivity;

import java.lang.ref.WeakReference;

/**
 * 为避免handler造成的内存泄漏
 * 1、使用静态的handler，对外部类不保持对象的引用
 * 2、但Handler需要与Activity通信，所以需要增加一个对Activity的弱引用
 */
public class WeakHandler extends Handler {
    private final WeakReference<Activity> mActivityReference;

    WeakHandler(Activity activity) {
        this.mActivityReference = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        MainActivity activity = (MainActivity) mActivityReference.get();  //获取弱引用队列中的activity
        switch (msg.what) {    //获取消息，更新UI
            case 1:
                byte[] data = (byte[]) msg.obj;
                break;
        }
    }
}
