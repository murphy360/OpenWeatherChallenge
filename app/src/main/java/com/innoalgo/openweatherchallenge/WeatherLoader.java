package com.innoalgo.openweatherchallenge;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Corey on 11/27/2016.
 */

public class WeatherLoader extends AsyncTaskLoader<ArrayList<DailyWeather>> {

    private String mUrl;
    final static String TAG = "WeatherLoader";//TODO TAG ALL FILES
    private final String API_KEY = "bf2eec4c617f2acfd321fa89f113bc45";//TODO add your API KEY here

    private final String URL_WEATHER_CURRENT = "http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial";
    private final String URL_WEATHER_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Atlanta,ga&units=imperial&cnt=5";

    public WeatherLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<DailyWeather> loadInBackground() {
        ArrayList<DailyWeather> earthquakes;

        if(mUrl.equalsIgnoreCase(URL_WEATHER_CURRENT)){
            Log.d(TAG, "loadInBackground: Current Weather");
            earthquakes = QueryHelper.extractCurrentWeather(mUrl + "&appid=" + API_KEY);
        }else{
            Log.d(TAG, "loadInBackground: Forecast");
            earthquakes = QueryHelper.extractForcastWeather(mUrl + "&appid=" + API_KEY);
        }

        return earthquakes;
    }
}