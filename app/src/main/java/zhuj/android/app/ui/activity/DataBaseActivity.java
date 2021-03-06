package zhuj.android.app.ui.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Random;

import zhuj.android.app.R;
import zhuj.android.base.activity.BaseActivity;
import zhuj.android.database.room.AndroidDatabase;
import zhuj.android.database.room.entity.User;
import zhuj.android.database.sqlitehelper.AppDatabase;
import zhuj.android.utils.log.Logger;
import zhuj.java.thread.WorkExecutor;

public class DataBaseActivity extends BaseActivity {

    private WorkExecutor workExecutor;
    private AppDatabase database;

    private TextView showText;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_database;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showText = findViewById(R.id.text_show);
        workExecutor = new WorkExecutor();
        database = new AppDatabase(this);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            showText.setText(showText.getText() + "\n" + msg.obj.toString());
            Log.d(TAG, "收到了消息");
        }
    };

    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.button_sql_insert) {
            Logger.d(TAG, "insert ");
            SQLiteDatabase database1 = database.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", new Random().nextInt(100));
            database1.insert("User", null, values);
        } else if (id == R.id.button_sql_query) {
            Logger.d(TAG, "query ");
            SQLiteDatabase database2 = database.getReadableDatabase();
            Cursor cursor = database2.query("User", new String[]{"id"}, " id < ? ", new String[]{"100"}, null, null, null);
            while (cursor.moveToNext()) {
                int a = cursor.getInt(0);
                showText.setText(showText.getText() + " \n column_id=" + a);
            }
        } else if (id == R.id.button_room_insert) {
            Logger.d(TAG, "room insert ");
            workExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    User u = new User();
                    u.setId(new Random().nextInt(100));
                    AndroidDatabase.getInstance(getActivity()).userDao().insert(u);
                }
            });
        } else if (id == R.id.button_room_query) {
            Logger.d(TAG, "room query ");
            workExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    List<User> users = AndroidDatabase.getInstance(getActivity()).userDao().getAllUser();
                    for (User user : users) {
                        Message message = Message.obtain();
                        message.obj = user;
                        mHandler.sendMessage(message);
                    }
                }
            });
        } else if (id == R.id.button_clear) {
            Logger.d(TAG, "clear text ");
            showText.setText("");
        }
    }
}