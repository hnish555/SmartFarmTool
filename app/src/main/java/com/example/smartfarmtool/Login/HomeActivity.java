package com.example.smartfarmtool.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartfarmtool.MainActivity;
import com.example.smartfarmtool.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TextView temp,city,decrip,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Button btn=findViewById(R.id.signout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


    }


}
