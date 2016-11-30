package com.innoalgo.openweatherchallenge;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Corey on 11/27/2016.
 */

public class WeatherLoader extends AsyncTaskLoader<ArrayList<DailyWeather>> {

    private String mUrl;
    final static String TAG = "WeatherLoader";
    private final String API_KEY = "bf2eec4c617f2acfd321fa89f113bc45";

    private final String URL_WEATHER_CURRENT = "http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial";
    private final String URL_WEATHER_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Atlanta,ga&units=imperial&cnt=6";

    private Context context;

    public WeatherLoader(Context context, String url) {
        super(context);
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
            forecastList = QueryHelper.getCurrentWeather(mUrl + "&appid=" + API_KEY, context);
        }else{
            forecastList = QueryHelper.getForecastWeather(mUrl + "&appid=" + API_KEY, context);
        }

        return forecastList;
    }
}