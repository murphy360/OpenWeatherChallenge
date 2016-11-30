package com.innoalgo.openweatherchallenge;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Corey on 11/29/2016.
 */

public class ForecastListAdapter extends BaseAdapter {
    public static final String TAG = "ForecastListAdapter";
    private ArrayList<DailyWeather> weatherList;
    private Context context;

    public ForecastListAdapter(Context context, ArrayList<DailyWeather> weatherList) {
           super();
        this.weatherList =  weatherList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public DailyWeather getItem(int i) {
        return weatherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.weather_list_item, parent, false);
        }

        // Get the {@link DailyWeather} object located at this position in the list
        DailyWeather mForecast = getItem(position);

        Log.d(TAG, "getView icon: " + mForecast.getWeatherIconText());
        ImageView weatherView = (ImageView) listItemView.findViewById(R.id.list_weather_image_view);
        weatherView.setImageBitmap(mForecast.getWeatherIcon(context));

        TextView dayTextView = (TextView) listItemView.findViewById(R.id.list_day);
        dayTextView.setText(mForecast.getDayOfWeek());

        TextView weatherTextView = (TextView) listItemView.findViewById(R.id.list_weather_text);
        weatherTextView.setText(mForecast.getCloudText());

        TextView maxTempTextView = (TextView) listItemView.findViewById(R.id.list_temp_current);
        int roundedMaxTemp = (int) Math.round(mForecast.getMaxTemp());
        String maxTemp = String.valueOf(roundedMaxTemp) + (char) 0x00B0;
        maxTempTextView.setText(maxTemp);

        TextView lowTempTextView = (TextView) listItemView.findViewById(R.id.list_temp_low);
        int roundedMinTemp = (int) Math.round(mForecast.getMinTemp());
        String minTemp = String.valueOf(roundedMinTemp) + (char) 0x00B0;
        lowTempTextView.setText(minTemp);

        return listItemView;
    }

    public void clear() {
        weatherList.clear();
    }

    public void add(ArrayList<DailyWeather> forecasts) {
        clear();
        weatherList.addAll(forecasts);
    }
}