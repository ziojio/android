package com.zhuj.android;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.zhuj.android.database.sqlitehelper.AppDatabase;
import com.zhuj.android.database.entity.User;
import com.zhuj.android.ui.activity.BaseActivity;
import com.zhuj.android.ui.activity.TestActivity;
import com.zhuj.android.ui.activity.WebViewActivity;
import com.zhuj.android.thread.WorkExecutor;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

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
        setSupportActionBar(findViewById(R.id.toolbar));
        showText = findViewById(R.id.text_show);
        workExecutor = new WorkExecutor();
        database = new AppDatabase(this);

//        final CenterDialog centerDialog = new CenterDialog();

        addClick(R.id.button_sql_insert, R.id.button_sql_query,
                R.id.button_room_insert, R.id.button_room_query,
                R.id.button_start, R.id.button_start_test, R.id.button_clear, R.id.button_webview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_sql_insert:
                Logger.d("insert ");
                SQLiteDatabase database1 = database.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", new Random().nextInt(100));
                database1.insert("User", null, values);
                break;
            case R.id.button_sql_query:
                Logger.d("query ");
                SQLiteDatabase database2 = database.getReadableDatabase();
                Cursor cursor = database2.query("User", new String[]{"id"}, " id < ? ", new String[]{"100"}, null, null, null);
                while (cursor.moveToNext()) {
                    int a = cursor.getInt(0);
                    showText.setText(showText.getText() + " \n column_id=" + a);
                }
                break;
            case R.id.button_room_insert:
                Logger.d("room insert ");
                workExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        User u = new User();
                        u.setId(new Random().nextInt(100));
                        App.getInstance().db().userDao().insert(u);
                    }
                });
                break;
            case R.id.button_room_query:
                Logger.d("room query ");
                workExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<User> users = App.getInstance().db().userDao().getAllUser();
                        for (User user : users) {
                            Message message = Message.obtain();
                            message.obj = user;
                            mHandler.sendMessage(message);
                        }
                    }
                });
                break;
            case R.id.button_clear:
                Logger.d("clear text ");
                showText.setText("");
                break;
            case R.id.button_start:
                Logger.d("start ");
                startActivity(new Intent(mActivity, com.yanzhenjie.recyclerview.sample.activity.MainActivity.class));
                break;
            case R.id.button_start_test:
                Logger.d("start test");
                startActivity(new Intent(mActivity, TestActivity.class));
                break;
            case R.id.button_webview:

                startActivity(new Intent(mActivity, WebViewActivity.class));
                break;
        }
    }

}
