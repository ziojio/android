package com.zhuj.android.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.zhuj.android.AppConfig;
import com.zhuj.android.database.room.dao.UserDao;
import com.zhuj.android.database.room.entity.User;

@Database(entities = {User.class}, version = AppConfig.DB_VERSION, exportSchema = false)
public abstract class AndroidDatabase extends RoomDatabase {
    AndroidDatabase() {
    }

    public abstract UserDao userDao();

}
