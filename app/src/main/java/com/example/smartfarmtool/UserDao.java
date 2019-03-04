package com.example.smartfarmtool;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user ")
    List<User> getAllUsers();

    @Insert
    void insertAll(User... users);
}
