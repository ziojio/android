package com.zhuj.android.data.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.zhuj.android.data.database.DBConfig;
import com.zhuj.android.data.database.room.dao.UserDao;
import com.zhuj.android.data.database.room.entity.User;

@Database(entities = {User.class}, version = DBConfig.ROOM_DB_VERSION, exportSchema = false)
public abstract class AndroidDatabase extends RoomDatabase {
    AndroidDatabase() {
    }

    public static AndroidDatabase INSTANCE;

    public static AndroidDatabase getInstance(Context appContext) {
        if (INSTANCE == null) {
            synchronized (AndroidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(appContext, AndroidDatabase.class, DBConfig.ROOM_DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();


}
