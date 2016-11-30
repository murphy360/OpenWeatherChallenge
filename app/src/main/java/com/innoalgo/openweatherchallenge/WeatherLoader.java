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
    private final String URL_WEATHER_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Atlanta,ga&units=imperial&cnt=6";

    private Context context;

    public WeatherLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        this.mUrl = url;
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<DailyWeather> loadInBackground() {
        ArrayList<DailyWeather> forecastList;

        if(mUrl.equalsIgnoreCase(URL_WEATHER_CURRENT)){
            Log.d(TAG, "loadInBackground: Current Weather");
            forecastList = QueryHelper.extractCurrentWeather(mUrl + "&appid=" + API_KEY, context);
        }else{
            Log.d(TAG, "loadInBackground: Forecast");
            forecastList = QueryHelper.extractForecastWeather(mUrl + "&appid=" + API_KEY, context);
        }

        return forecastList;
    }
}