package zhuj.android.database.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import zhuj.android.database.room.entity.User;

@Dao
public abstract class UserDao {

    @Query("select * from user")
    public abstract List<User> getAllUser();

    @Query("select * from user where id=:id")
    public abstract User getUser(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<User> users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(User user);

    @Update
    public abstract void update(User user);

    @Delete
    public abstract void delete(User user);

}
