package com.example.smartfarmtool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class OtherCostRepo {
    private DataObjectOtherCosts object;
    OtherCostRepo(Context context) {
        CropDatabase database = CropDatabase.getDatabase(context);
        object = database.otherCosts();
    }

    void insertCost(final OtherCropCost cropCost) {
        new OtherCostRepo.insertAsyncTask(object).execute(cropCost);
    }

    List<OtherCropCost> fetchAllCosts(int cropId) {
        return object.fetchAllCosts(cropId);
    }


    private static class insertAsyncTask extends AsyncTask<OtherCropCost, Void, Void> {

        private DataObjectOtherCosts mAsyncTaskDao;

        insertAsyncTask(DataObjectOtherCosts dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(OtherCropCost... cost) {
            mAsyncTaskDao.insertCost(cost[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Debug","Inserted");
        }
    }

}
