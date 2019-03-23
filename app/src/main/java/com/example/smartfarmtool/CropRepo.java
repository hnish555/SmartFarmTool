package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CropRepo {

    private DataObject object;
    CropRepo(Context context) {
        CropDatabase database = CropDatabase.getDatabase(context);
        object = database.dataObject();
    }

    void insertCrop(final CropDetail detail) {
        new insertAsyncTask(object).execute(detail);
    }

    void updateCrop(CropDetail detail){new updateAsyncTask(object).execute(detail);}

    public CropDetail getCrop(int id) {
        return object.getCrop(id);
    }

    List<CropDetail> fetchAllCrops() {
        return object.fetchAllCrops();
    }

    void deleteAll() {
        new DeleteAsync(object).execute();
    }

    private static class insertAsyncTask extends AsyncTask<CropDetail, Void, Void> {

        private DataObject mAsyncTaskDao;

        insertAsyncTask(DataObject dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CropDetail... crop) {
            mAsyncTaskDao.insertCrop(crop[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Debug","Inserted");
        }
    }

    private static class updateAsyncTask extends AsyncTask<CropDetail, Void, Void> {

        private DataObject mAsyncTaskDao;

        updateAsyncTask(DataObject dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CropDetail... crop) {
            mAsyncTaskDao.updateCrop(crop[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Debug","Inserted");
        }
    }

    private static class DeleteAsync extends AsyncTask<CropDetail, Void, Void> {

        private DataObject mAsyncTaskDao;

        DeleteAsync(DataObject dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(CropDetail... crop) {
            mAsyncTaskDao.deleteAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Debug","Deleted");
        }
    }
}


