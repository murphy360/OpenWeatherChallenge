/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.innoalgo.openweatherchallenge;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link ForecastListAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link DailyWeather} objects.
 */
public class ForecastListAdapter extends BaseAdapter {
    private ArrayList<DailyWeather> weatherList;
    public static final String TAG = "ForecastListAdapter";
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
        maxTempTextView.setText(String.valueOf(roundedMaxTemp) + (char) 0x00B0);

        TextView lowTempTextView = (TextView) listItemView.findViewById(R.id.list_temp_low);
        int roundedMinTemp = (int) Math.round(mForecast.getMinTemp());
        lowTempTextView.setText(String.valueOf(roundedMinTemp) + (char) 0x00B0);

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