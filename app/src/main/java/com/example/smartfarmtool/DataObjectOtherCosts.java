package com.example.smartfarmtool;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DataObjectOtherCosts {
    @Insert
    void insertCost(OtherCropCost cropCost);

    @Query("SELECT * FROM OtherCropCost WHERE id =:cropId")
    List<OtherCropCost>fetchAllCosts(int cropId);

}