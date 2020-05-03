package com.zhuj.android;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.orhanobut.logger.Logger;
import com.zhuj.android.database.entity.User;
import com.zhuj.android.ui.activity.BaseActivity;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.ui.dialog.CenterDialog;
import com.zhuj.code.thread.WorkExecutor;

import java.util.List;


public class MainActivity extends BaseActivity {

    private MainActivity mainActivity;

    private WorkExecutor workExecutor;

    private TextView showText;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            showText.setText(showText.getText() + "\n" + msg.obj.toString());
            Logger.d("收到了消息");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        setSupportActionBar(findViewById(R.id.toolbar));
        showText = findViewById(R.id.text_show);
        workExecutor = new WorkExecutor();

//        final CenterDialog centerDialog = new CenterDialog();

        addClick(R.id.button_a, R.id.button_start, R.id.button_query);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_a:
                Logger.d("insert ");
                User user = new User();
                user.setPhone(String.valueOf((Math.random() * 1000000) / 1));
                user.setPassword("123456");
                workExecutor.execute(() -> App.db().userDao().insert(user));
                break;
            case R.id.button_start:
                Logger.d("start ");
                mHandler.sendMessageDelayed(new Message(), 10 * 1000);
                startActivity(new Intent(mainActivity, TestActivity.class));
                finish();
                break;
            case R.id.button_query:
                Logger.d("query ");
                workExecutor.execute(() -> {
                    List<User> users = App.db().userDao().getAllUser();
                    for (User u : users) {
                        Message msg = new Message();
                        msg.obj = u;
                        mHandler.sendMessage(msg);
                    }
                });
                break;
        }
    }
}
