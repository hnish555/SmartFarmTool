package com.example.smartfarmtool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CropListingActivity extends AppCompatActivity {

    FloatingActionButton add_new_crop;
    static RecyclerView rv;
    static CropListingAdapter adapter;
    static List<CropDetail> array;
    static TextView no_crop;
    CropRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_listing);

        no_crop = findViewById(R.id.no_crop);
        add_new_crop = findViewById(R.id.add_new_crop);
        rv = findViewById(R.id.crop_list);

        repo = new CropRepo(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        rv.setItemAnimator(new DefaultItemAnimator());

        GetAsyncTask task = new GetAsyncTask(this,repo);
        task.execute();

        add_new_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CropListingActivity.this, AddCropActivity.class));
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0) {
                    add_new_crop.animate().scaleX(0f).scaleY(0f).setDuration(300);
                    add_new_crop.setEnabled(false);
                }
                else if (dy<0) {
                    add_new_crop.animate().scaleX(1f).scaleY(1f).setDuration(300);
                    add_new_crop.setEnabled(true);
                }

            }
        });
    }

    private static class GetAsyncTask extends AsyncTask<Void, Void, Void> {

        CropRepo repo;
        Context mContext;

        GetAsyncTask(Context context, CropRepo messageRepo) {
            mContext = context.getApplicationContext();
            repo = messageRepo;
        }

        @Override
        protected Void doInBackground(Void... params) {
            array = repo.fetchAllCrops();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (array != null) {
                if (array.size() > 0) {
                    no_crop.setVisibility(View.INVISIBLE);
                    adapter = new CropListingAdapter(mContext, array);
                    rv.setAdapter(adapter);
                } else
                    no_crop.setVisibility(View.VISIBLE);
            } else
                no_crop.setVisibility(View.VISIBLE);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetAsyncTask task = new GetAsyncTask(this,repo);
        task.execute();
    }
}
