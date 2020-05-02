package com.zhuj.android.database;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.zhuj.android.database.dao.UserDao;
import com.zhuj.android.database.entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AndroidDatabase extends RoomDatabase {
    public AndroidDatabase() {
    }

    public abstract UserDao userDao();

}
