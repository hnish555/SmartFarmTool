package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner cropname;
    public String cropnames;
    EditText quantity;
    EditText rate;
    Button button;
    public String currentdate;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        cropnames = adapterView.getItemAtPosition(i).toString();



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        cropname = findViewById(R.id.cropname);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.crops_name,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropname.setAdapter(adapter);
        cropname.setOnItemSelectedListener(this);
        quantity = findViewById(R.id.quantity);
        rate = findViewById(R.id.rate);
        button = findViewById(R.id.sellbutton);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int dd = calendar.get(calendar.DAY_OF_MONTH);
        int mm = calendar.get(calendar.MONTH);
        int yy = calendar.get(calendar.YEAR);
        currentdate = dd+"/"+(mm+1)+"/"+yy;

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"product")
                .allowMainThreadQueries().build();




            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if( cropnames == null || ( TextUtils.isEmpty(quantity.getText()) || TextUtils.isEmpty(rate.getText())))
                    {

                        Toast.makeText(getApplicationContext(),"Please fill all entry before adding the product",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        User user = new User(cropnames, quantity.getText().toString(), rate.getText().toString(),currentdate);
                        db.userDao().insertAll(user);
                        Intent intent = new Intent(AddProduct.this, SellingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }




                }
            });



    }


}
