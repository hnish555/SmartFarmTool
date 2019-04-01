package com.example.smartfarmtool.Login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.smartfarmtool.Buy;
import com.example.smartfarmtool.HomeAdapter;
import com.example.smartfarmtool.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Buy> list;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.home_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<Buy>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("AllCrops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Buy buy=dataSnapshot1.getValue(Buy.class);
                    list.add(buy);
                }
                homeAdapter=new HomeAdapter(HomeActivity.this,list);
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
