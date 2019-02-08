package com.example.smartfarmtool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.xml.transform.Source;

public class MainActivity extends AppCompatActivity {

    Button farmersection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        farmersection = (Button) findViewById(R.id.farmer_section);
        farmersection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Login_Signup.class);
                startActivity(intent);
            }
        });

    }
}
