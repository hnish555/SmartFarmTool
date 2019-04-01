package com.example.smartfarmtool;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.CityPreference;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class Main2Activityweather extends AppCompatActivity {


    private TextView cityName;
    private ImageView iconView;
    private TextView temp;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    Weather weather = new Weather();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_activityweather);


        cityName = (TextView) findViewById(R.id.cityText);
        iconView = (ImageView) findViewById(R.id.thumbnailIcon);
        temp = (TextView) findViewById(R.id.tempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        sunrise = (TextView) findViewById(R.id.riseText);
        sunset = (TextView) findViewById(R.id.setText);
        updated = (TextView) findViewById(R.id.updateText);



        CityPreference cityPreference = new CityPreference(Main2Activityweather.this);
        renderWeatherData(cityPreference.getCity());



    }


    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&appid=104872bdd404e3996af2ff4d0a9fcfbf"});


    }
    private class WeatherTask extends AsyncTask<String, Void, Weather>{


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

            String sunriseDate = df.format(new Date(weather.place.getSunrise()));
            String sunsetDate = df.format(new Date(weather.place.getSunset()));
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
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise : " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate  );
            updated.setText("Last Updated: " + updateD );
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() + ")");


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.citychanger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        int id = item.getItemId();


        if (id == R.id.change_cityId) {



          AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activityweather.this);

            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
            mBuilder.setTitle("Change City");
            final Spinner mSpineer = (Spinner) mView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (Main2Activityweather.this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.city_name));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpineer.setAdapter(adapter);

            mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    CityPreference cityPreference = new CityPreference(Main2Activityweather.this);
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

        }

        return super.onOptionsItemSelected(item);
    }




}
