package com.example.smartfarmtool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.smartfarmtool.Data.CityPreference;
import com.example.smartfarmtool.Data.JSONWeatherParser;
import com.example.smartfarmtool.Data.WeatherHttpClient;
import com.example.smartfarmtool.Main.NewsMainActivity;
import com.example.smartfarmtool.WeatherModel.Weather;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NavigationDMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView cityName;
    private ImageView iconView;
    private TextView temp;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView updated;
    Weather weather = new Weather();

    private DatabaseReference mDatabaseReference;
    private RecyclerView recyclerView;
    private BuyRecyclerAdapter buyRecyclerAdapter;
    private List<Buy> buyList;
    private FirebaseDatabase mDatabase;

    private FirebaseUser mUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_dmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);

//        updated = (TextView) findViewById(R.id.updateText);
        CityPreference cityPreference = new CityPreference(NavigationDMenu.this);
        renderWeatherData(cityPreference.getCity());

        recyclerView =findViewById(R.id.buyRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buyList = new ArrayList<Buy>();

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("AllCrops");
        mDatabaseReference.keepSynced(true);


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Buy ur=dataSnapshot1.getValue(Buy.class);
                    buyList.add(ur);


                }
                Collections.reverse(buyList);
                buyRecyclerAdapter=new BuyRecyclerAdapter(NavigationDMenu.this,buyList);
                recyclerView.setAdapter(buyRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&appid=104872bdd404e3996af2ff4d0a9fcfbf"});


    }
    private class WeatherTask extends AsyncTask<String, Void, Weather> {


        @Override
        protected Weather doInBackground(String... strings) {



            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));

            weather = JSONWeatherParser.getWeather(data);
            Log.v("Data: ", weather.place.getCity());

            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather)
        {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();

//            String sunriseDate = df.format(new Date(weather.place.getSunrise()));
//            String sunsetDate = df.format(new Date(weather.place.getSunset()));
            DecimalFormat decimalFormat = new DecimalFormat("0.00");

            String updateD = df.format(new Date(weather.place.getLastupdate()));

            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            double temp1;

            double d = Double.parseDouble(tempFormat);



            temp1 = d - 273.15;

            //°C

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            temp.setText("" + temp1 );
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind:" + weather.wind.getSpeed() + "mps");

//            updated.setText(updateD );
            description.setText( weather.currentCondition.getCondition());


        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_dmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.news_menu_id) {
            startActivity(new Intent(NavigationDMenu.this, NewsMainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            return true;
        }
        if (id==R.id.change_cityId){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavigationDMenu.this);

            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
            mBuilder.setTitle("Change City");
            final Spinner mSpineer = (Spinner) mView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (NavigationDMenu.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.city_name));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpineer.setAdapter(adapter);

            mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    CityPreference cityPreference = new CityPreference(NavigationDMenu.this);
                    cityPreference.setCity(mSpineer.getSelectedItem().toString());

                    String newCity = cityPreference.getCity();

                    renderWeatherData(newCity);

                }
            });

            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.dismiss();
                }
            });

            mBuilder.setView(mView);
            AlertDialog alertDialog = mBuilder.create();
            alertDialog.show();
            return true;
        }

        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(NavigationDMenu.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            return true;
        }
        if (id == R.id.contactus) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            startActivity(new Intent(NavigationDMenu.this,CropListingActivity.class));
        } else if (id == R.id.nav_myaccount) {

        } else if (id == R.id.nav_selling) {

            startActivity(new Intent(NavigationDMenu.this,SellingActivity.class));

        } else if (id == R.id.nav_buying) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
