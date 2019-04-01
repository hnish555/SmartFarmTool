package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import java.util.List;

public class SellingActivity extends AppCompatActivity {

    public static final String TAG = "SellingActivity";

    static RecyclerView recyclerView;
    static UserAdapter adapter;
    FloatingActionButton fab;
    static List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.sellrecyclerview);

        UserRepo repo = new UserRepo(this);
        GetAsyncTask asyncTask = new GetAsyncTask(this, repo);
        asyncTask.execute();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: pressed!");
                Intent intent = new Intent(SellingActivity.this, AddProduct.class);
                startActivity(intent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0) {
                    fab.show();

                } else if (dy > 0) {

                    fab.hide();
                }
            }
        });


    }

    private static class GetAsyncTask extends AsyncTask<Void, Void, Void> {

        UserRepo repo;
        Context mContext;

        GetAsyncTask(Context context, UserRepo messageRepo) {
            mContext = context.getApplicationContext();
            repo = messageRepo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            users = repo.getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (users != null) {
                if (users.size() > 0) {
                    adapter = new UserAdapter(users);
                    recyclerView.setAdapter(adapter);
                }


            }

        }

    }
}