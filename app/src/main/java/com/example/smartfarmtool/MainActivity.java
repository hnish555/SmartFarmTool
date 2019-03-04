package com.example.smartfarmtool;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.smartfarmtool.BuyActivity.ProductBuy;
import com.example.smartfarmtool.Login.Login_Signup;
import com.google.firebase.FirebaseApp;


public class MainActivity extends AppCompatActivity {

    Button farmersection,sellButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        farmersection = (Button) findViewById(R.id.farmer_section);
        sellButton=findViewById(R.id.sell_buy);

        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ProductBuy.class));
            }
        });

        farmersection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login_Signup.class);
                startActivity(intent);
            }
        });

    }

}
