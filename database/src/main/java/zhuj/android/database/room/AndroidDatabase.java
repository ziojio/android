package zhuj.android.database.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import zhuj.android.database.DBConfig;
import zhuj.android.database.room.dao.UserDao;
import zhuj.android.database.room.entity.User;


@Database(entities = {User.class}, version =  DBConfig.ROOM_DB_VERSION, exportSchema = false)
public abstract class AndroidDatabase extends RoomDatabase {
    AndroidDatabase() {
    }

    public static AndroidDatabase INSTANCE;

    public static AndroidDatabase getInstance(Context appContext) {
        if (INSTANCE == null) {
            synchronized (AndroidDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(appContext, AndroidDatabase.class,  DBConfig.ROOM_DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();


}
