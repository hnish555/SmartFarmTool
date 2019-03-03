package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProduct extends AppCompatActivity {

    EditText cropname;
    EditText quantity;
    EditText rate;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        cropname = findViewById(R.id.crop_name);
        quantity = findViewById(R.id.quantity);
        rate = findViewById(R.id.rate);
        button = findViewById(R.id.sellbutton);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"product")
                .allowMainThreadQueries().build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User(cropname.getText().toString(),quantity.getText().toString(),rate.getText().toString());
                db.userDao().insertAll(user);
                startActivity(new Intent(AddProduct.this,SellingActivity.class));

            }
        });

    }
}
