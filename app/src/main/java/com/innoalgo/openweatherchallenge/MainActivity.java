package com.innoalgo.openweatherchallenge;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<DailyWeather>>{

    private final String URL_WEATHER_CURRENT = "http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial";
    private final String API_KEY = "bf2eec4c617f2acfd321fa89f113bc45";//TODO add your API KEY here

    private static final int WEATHER_LOADER_ID = 1;

    protected static final int INTENT_DETAILS = 2;


    private ListView forcecastListView;
    private ForecastListAdapter adapter;
    private String TAG = "MAIN ACTIVITY: ";
    private DailyWeather todayWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectUserInterface();

        getLoaderManager().initLoader(WEATHER_LOADER_ID, null, this).forceLoad();


    }

    private void connectUserInterface() {
        adapter = new ForecastListAdapter(
                this, new ArrayList<DailyWeather>());

        forcecastListView = (ListView) findViewById(R.id.forcecast_list_view);
        forcecastListView.setAdapter(adapter);
        forcecastListView.setClickable(true);
        forcecastListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Get SensorObject from listView
                DailyWeather dailyWeather = (DailyWeather) (forcecastListView.getItemAtPosition(position));
                Intent intent = new Intent(view.getContext(),
                        DetailsViewActivity.class);
                intent.putExtra("weather", dailyWeather);
                startActivityForResult(intent, INTENT_DETAILS);
            }
        });
    }

    private void updateUI(ArrayList<DailyWeather> forcecasts) {
        Log.d(TAG, "updateUI: " + forcecasts.size());
        adapter.clear();
        adapter.add(forcecasts);

        TextView date = (TextView) findViewById(R.id.today_text_date);

    }

    @Override
    public Loader<ArrayList<DailyWeather>> onCreateLoader(int i, Bundle bundle) {

        return new WeatherLoader(this, URL_WEATHER_CURRENT + "&appid=" + API_KEY);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DailyWeather>> loader, ArrayList<DailyWeather> dailyWeathers) {

        if (dailyWeathers != null && dailyWeathers.size() == 1) {
            todayWeather = dailyWeathers.get(0);
        }else if(dailyWeathers != null && !dailyWeathers.isEmpty()){
            updateUI(dailyWeathers);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DailyWeather>> loader) {
        adapter.clear();
    }
}
