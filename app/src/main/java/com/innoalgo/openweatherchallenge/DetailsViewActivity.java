package com.innoalgo.openweatherchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Corey on 11/29/2016.
 */

public class DetailsViewActivity extends AppCompatActivity {

    private DailyWeather dailyWeather;
    private String TAG = "DETAILS ACTIVITY: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        getExtras();
        connectUserInterface();
    }

    private void getExtras() {
        Intent intent = this.getIntent();
        dailyWeather = (DailyWeather) intent.getSerializableExtra("weather");
    }

    private void connectUserInterface() {

        TextView day = (TextView) findViewById(R.id.details_day_text);
        day.setText(dailyWeather.getDayOfWeek());

        TextView date = (TextView) findViewById(R.id.details_date_text);

        // November, 29
        DateFormat format = new SimpleDateFormat("\nMMMM, dd");
        String dateText = format.format(dailyWeather.getDate());
        date.setText(dateText);

        TextView maxTemp = (TextView) findViewById(R.id.details_temp_current_text);
        int roundedTemp;
        if(dailyWeather.getDayOfWeek().equalsIgnoreCase("Today")){
            roundedTemp = (int) Math.round(dailyWeather.getCurrentTemp());
        }else{
            roundedTemp = (int) Math.round(dailyWeather.getMaxTemp());
        }
        maxTemp.setText(String.valueOf(roundedTemp) + (char) 0x00B0);

        TextView lowTemp = (TextView) findViewById(R.id.details_temp_low_text);
        int roundedMinTemp = (int) Math.round(dailyWeather.getMinTemp());
        lowTemp.setText(String.valueOf(roundedMinTemp) + (char) 0x00B0);

        ImageView weatherIcon = (ImageView) findViewById(R.id.details_weather_image_view);
        weatherIcon.setImageBitmap(dailyWeather.getWeatherIcon(this));

        TextView weather = (TextView) findViewById(R.id.details_weather_text);
        weather.setText(dailyWeather.getCloudText());

        TextView humidity = (TextView) findViewById(R.id.details_humidity_text);
        humidity.setText("Humidity: " + dailyWeather.getHumidity() + "%");

        TextView pressure = (TextView) findViewById(R.id.details_pressure_text);
        pressure.setText("Pressure: " + dailyWeather.getPressure() + " hPa");

        TextView wind = (TextView) findViewById(R.id.details_wind_text);
        String windDirection = getCardinalDirections(dailyWeather.getWindDirection());
        double windSpeed = dailyWeather.getWindSpeed();
        wind.setText("Wind: " + windSpeed + " km/h " + windDirection);




    }

    //Should be using points (11.25 degrees) but will make due with ints
    private String getCardinalDirections(int windDirection){
        String cardinalDirection = "";
        if(windDirection > (337) || windDirection < (23) ){
            cardinalDirection = "N";
        }else if(windDirection > 23 && windDirection < 68){
            cardinalDirection = "NE";
        }else if(windDirection > 67 && windDirection < 113){
            cardinalDirection = "E";
        }else if(windDirection > 112 && windDirection < 158){
            cardinalDirection = "SE";
        }else if(windDirection > 157 && windDirection < 203){
            cardinalDirection = "S";
        }else if(windDirection > 202 && windDirection < 248){
            cardinalDirection = "SW";
        }else if(windDirection > 247 && windDirection < 293){
            cardinalDirection = "W";
        }else if(windDirection > 292 && windDirection < 338){
            cardinalDirection = "NW";
        }
        return cardinalDirection;
    }
}
