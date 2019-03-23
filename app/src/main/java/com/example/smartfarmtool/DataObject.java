package com.example.smartfarmtool;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface DataObject {
    @Insert
    void insertCrop(CropDetail detail);


    @Query("SELECT * FROM CurrentCrops")
    List<CropDetail>fetchAllCrops();

    @Update
    void updateCrop(CropDetail detail);


    @Query("SELECT * FROM CurrentCrops WHERE id =:cropId")
    CropDetail getCrop(int cropId);

    @Query("Delete from CurrentCrops")
    void deleteAll();


    @Delete
    void deleteMessage(CropDetail detail);
}