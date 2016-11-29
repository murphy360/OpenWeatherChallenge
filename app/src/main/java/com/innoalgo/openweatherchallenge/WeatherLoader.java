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
        ArrayList<DailyWeather> earthquakes = QueryHelper.extractCurrentWeather(mUrl);
        Log.d(TAG, "loadInBackground: ");
        return earthquakes;
    }
}