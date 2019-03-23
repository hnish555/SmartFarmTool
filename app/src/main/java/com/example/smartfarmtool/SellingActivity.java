package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.nfc.Tag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import java.util.List;

public class SellingActivity extends AppCompatActivity {

    public static final String TAG ="SellingActivity";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selling);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        



        recyclerView = findViewById(R.id.sellrecyclerview);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"product")
                .allowMainThreadQueries().build();

        List<User> users = db.userDao().getAllUsers();




       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       adapter = new UserAdapter(users);
       recyclerView.setAdapter(adapter);






        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"onClick: pressed!");
                Intent intent = new Intent(SellingActivity.this,AddProduct.class);
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

                if (dy<0)
                {
                   fab.show();

                }
                else if (dy > 0){

                    fab.hide();
                }
            }
        });




    }

}
