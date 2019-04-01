package com.example.smartfarmtool.Data;


import com.example.smartfarmtool.Util.WeatheUtils;
import com.example.smartfarmtool.WeatherModel.Place;
import com.example.smartfarmtool.WeatherModel.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {


    public static Weather getWeather(String data)

    {
        Weather weather = new Weather();

        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();
            JSONObject coordObj = WeatheUtils.getObject("coord", jsonObject);
            place.setLat(WeatheUtils.getFloat("lat", coordObj));
            place.setLon(WeatheUtils.getFloat("lon", coordObj));


            JSONObject sysObj = WeatheUtils.getObject("sys", jsonObject);
            place.setCountry(WeatheUtils.getString("country", sysObj));
            place.setLastupdate(WeatheUtils.getInt("dt", jsonObject));
            place.setSunrise(WeatheUtils.getInt("sunrise", sysObj));
            place.setSunset(WeatheUtils.getInt("sunset", sysObj));
            place.setCity(WeatheUtils.getString("name", jsonObject));
            weather.place = place;


            JSONArray jsonArray = jsonObject.getJSONArray("weather");


            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(WeatheUtils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(WeatheUtils.getString("description", jsonWeather));
            weather.currentCondition.setCondition(WeatheUtils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(WeatheUtils.getString("icon", jsonWeather));


            JSONObject mainObj = WeatheUtils.getObject("main", jsonObject);
            weather.currentCondition.setHumidity(WeatheUtils.getInt("humidity", mainObj));
            weather.currentCondition.setPressure(WeatheUtils.getInt("pressure", mainObj));
            weather.currentCondition.setMinTemperature(WeatheUtils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMinTemperature(WeatheUtils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemperature(WeatheUtils.getDouble("temp", mainObj));




            JSONObject windObj = WeatheUtils.getObject("wind", jsonObject);
            weather.wind.setSpeed(WeatheUtils.getFloat("speed", windObj));
            weather.wind.setDeg(WeatheUtils.getFloat("deg", windObj));


            JSONObject cloudObj = WeatheUtils.getObject("clouds", jsonObject);
            weather.clouds.setPercipitation(WeatheUtils.getInt("all", cloudObj));


            return weather;




        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }

    }
}

