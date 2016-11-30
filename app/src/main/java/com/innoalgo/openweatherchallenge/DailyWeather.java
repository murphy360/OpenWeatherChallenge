package com.innoalgo.openweatherchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Corey on 11/29/2016.
 */

public class DailyWeather implements Serializable {

    private Date date;
    private Double currentTemp;
    private Double minTemp;
    private Double maxTemp;
    private Double humidity;
    private Double pressure;
    private String cloudText;
    private String cloudDescription;
    private String iconText;
    private Double windSpeed;
    private int windDirection;
    private Bitmap bitmap;


    private String TAG = "DailyWeather: ";

    public DailyWeather(Date d, Double temp, Double tempMin, Double tempMax, Double humidity,
                        Double pressure, String cloudText, String cloudDescription, String iconText,
                        Double windSpeed, int windDirection, Context c) {
        Log.d(TAG, "Date: " + d.toString());
        this.date = d;
        this.currentTemp = temp;
        this.minTemp = tempMin;
        this.maxTemp = tempMax;
        this.humidity = humidity;
        this.pressure = pressure;
        this.cloudText = cloudText;
        this.cloudDescription = cloudDescription;
        this.iconText = iconText;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        String mUrl = "http://openweathermap.org/img/w/";
        byte[] mBytes = QueryHelper.imageByter(c, mUrl + this.iconText + ".png");
        bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);

    }

    public DailyWeather() {

    }

    //TODO Create Constructor

    public String getWeatherIconText() {
        return iconText;
    }

    public String getDayOfWeek() {
        Date today = new Date();
        today.setHours(1);
        today.setMinutes(0);
        today.setSeconds(0);
        Log.d(TAG, "getDayOfWeek: \n" + date.toString() + "\n" + today.toString());
        if(date.getDay() - today.getDay() == 0){
            return "Today";
        }else if(date.getDay() - today.getDay() == 1
                || date.getDay() - today.getDay() == -6 ){
            return "Tomorrow";
        }else{
            DateFormat format = new SimpleDateFormat("EEEE");
            return format.format(date);
        }
    }

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public Date getDate() {
        return date;
    }

    public String getCloudDescription() {
        return cloudDescription;
    }

    public String getCloudText() {
        return cloudText;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public Bitmap getWeatherIcon() {
        return bitmap;
    }
}
