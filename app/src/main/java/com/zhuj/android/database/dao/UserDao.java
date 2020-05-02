package com.zhuj.android.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zhuj.android.database.entity.User;

import java.util.List;

@Dao
public abstract class UserDao {

    @Query("select * from user")
    public abstract List<User> getAllUser();

    @Query("select * from user where id=:id")
    public abstract User getUser(int id);

    @Insert
    public abstract void insert(List<User> users);

    @Insert
    public abstract void insert(User user);

    @Update
    public abstract void update(User user);

    @Delete
    public abstract void delete(User user);

}
