package com.example.smartfarmtool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class UserRepo {
    private UserDao object;
    UserRepo(Context context) {
        CropDatabase database = CropDatabase.getDatabase(context);
        object = database.userDao();
    }

    void insertAll(final User user) {
        new insertAsyncTask(object).execute(user);
    }

    List<User> getAllUsers() {
        return object.getAllUsers();
    }


    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(User... cost) {
            mAsyncTaskDao.insertAll(cost[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Debug","Inserted");
        }
    }

}
