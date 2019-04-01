package com.example.smartfarmtool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.smartfarmtool.CropListingActivity.adapter;
import static com.example.smartfarmtool.CropListingActivity.rv;

public class AddCropActivity extends AppCompatActivity {

    String[] cropArray = {"Wheat", "Corn", "Rice", "Jowar", "Pulses"};
    String crop = "Wheat";
    String date;
    double lat, lng;
    Location lastLocation;
    LocationManager mLocManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        Spinner spin = findViewById(R.id.spinner);

        final TextView location_detail = findViewById(R.id.location_detail);
        final EditText prod_land = findViewById(R.id.prod_land);
        FloatingActionButton get_location = findViewById(R.id.get_location);
        Button add_crop_button = findViewById(R.id.add_crop_button);

        location_detail.setAlpha(0f);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cropArray);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        spin.setAdapter(aa);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        date = dateFormat.format(today);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crop = cropArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(AddCropActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddCropActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddCropActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                } else {
                    lastLocation = mLocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (lastLocation != null) {
                        lat = lastLocation.getLatitude();
                        lng = lastLocation.getLongitude();
                        location_detail.setText(getAddress(lat,lng));
                        location_detail.animate().alpha(1f).setDuration(300);

                    } else {
                        Toast.makeText(AddCropActivity.this, "Turn On Location", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        add_crop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prod_land.getText().toString().equals("")) {
                    prod_land.setError("Field can't be empty");
                } else {
                    CropRepo repo = new CropRepo(AddCropActivity.this);
                    CropDetail detail = new CropDetail(crop, Integer.parseInt(prod_land.getText().toString()), lat, lng, date);
                    repo.insertCrop(detail);
                    AddCropActivity.this.finish();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(lastLocation!=null) {
                    lat = lastLocation.getLatitude();
                    lng = lastLocation.getLongitude();
                }
            }
        }
    }

    public String getAddress(double lat, double lng) {
        String add = "";
        Geocoder geocoder = new Geocoder(AddCropActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getSubAdminArea();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
}


