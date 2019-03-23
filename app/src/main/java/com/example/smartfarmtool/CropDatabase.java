package com.example.smartfarmtool;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {CropDetail.class,OtherCropCost.class}, version = 1, exportSchema = false)
public abstract class CropDatabase extends RoomDatabase {

    public abstract DataObject dataObject();
    public abstract DataObjectOtherCosts otherCosts();
    private static volatile CropDatabase INSTANCE;

    static CropDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CropDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CropDatabase.class, "db_crops")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

