package com.zhuj.android.database.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.zhuj.android.database.DBConfig;

public class AppDatabase extends SQLiteOpenHelper {

    public AppDatabase(@Nullable Context context) {
        super(context, DBConfig.SQLITE_DB_NAME, null, DBConfig.SQLITE_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbl_user = "CREATE TABLE IF NOT EXISTS User(" +
                " id        int           primary key ," +
                " password  varchar(32)  ," +
                " name      varchar(16)  ," +
                " email     varchar(64)  ," +
                " phone     varchar(16)  )";
        db.beginTransaction();
        db.execSQL(tbl_user);
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
