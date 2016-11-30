package com.innoalgo.openweatherchallenge;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<DailyWeather>>{

    private final String URL_WEATHER_CURRENT = "http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial";
    private final String URL_WEATHER_FORECAST = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Atlanta,ga&units=imperial&cnt=6";

    private static final int WEATHER_LOADER_TODAY_ID = 1;
    private static final int WEATHER_LOADER_FORECAST_ID = 2;

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


        getLoaderManager().initLoader(WEATHER_LOADER_FORECAST_ID, null, this).forceLoad();
        getLoaderManager().initLoader(WEATHER_LOADER_TODAY_ID, null, this).forceLoad();


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

        LinearLayout todayLinearLayout = (LinearLayout) findViewById(R.id.today_linear_layout);
        todayLinearLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),
                        DetailsViewActivity.class);
                intent.putExtra("weather", todayWeather);
                startActivityForResult(intent, INTENT_DETAILS);
            }

        });
    }



    @Override
    public Loader<ArrayList<DailyWeather>> onCreateLoader(int i, Bundle bundle) {

        if(i == WEATHER_LOADER_FORECAST_ID){

            return new WeatherLoader(this, URL_WEATHER_CURRENT);
        }else{
            return new WeatherLoader(this, URL_WEATHER_FORECAST);
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DailyWeather>> loader, ArrayList<DailyWeather> dailyWeathers) {

        if (dailyWeathers != null && dailyWeathers.size() == 1) {
            Log.d(TAG, "onLoadFinished: today");
            todayWeather = dailyWeathers.get(0);
            updateToday();
        }else if(dailyWeathers != null && !dailyWeathers.isEmpty()){
            Log.d(TAG, "onLoadFinished: Forecast");
            updateUI(dailyWeathers);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DailyWeather>> loader) {
        adapter.clear();
    }

    private void updateToday() {
        Log.d(TAG, "updateToday: ");

        TextView date = (TextView) findViewById(R.id.today_text_date);

        // November, 29
        DateFormat format = new SimpleDateFormat(" MMMM, dd");
        String dateText = format.format(todayWeather.getDate());
        date.setText(todayWeather.getDayOfWeek() + dateText);

        TextView temp = (TextView) findViewById(R.id.today_text_temp_current);
        int roundedCurrentTemp = (int) Math.round(todayWeather.getCurrentTemp());
        temp.setText(String.valueOf(roundedCurrentTemp) + (char) 0x00B0);

        TextView lowTemp = (TextView) findViewById(R.id.today_text_temp_low);
        int roundedMinTemp = (int) Math.round(todayWeather.getMinTemp());
        lowTemp.setText(String.valueOf(roundedMinTemp) + (char) 0x00B0);

        Log.d(TAG, "updateToday icon: " + todayWeather.getWeatherIconText());
        ImageView weatherIcon = (ImageView) findViewById(R.id.today_image_icon);
        weatherIcon.setImageBitmap(todayWeather.getWeatherIcon(this));


        TextView weather = (TextView) findViewById(R.id.today_text_description);
        weather.setText(todayWeather.getCloudText());


    }

    private void updateUI(ArrayList<DailyWeather> forcecasts) {
        Log.d(TAG, "updateUI: " + forcecasts.size());
        adapter.clear();
        adapter.add(forcecasts);
        adapter.notifyDataSetChanged();


    }
}
