package com.innoalgo.openweatherchallenge;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Corey on 11/29/2016.
 */

public class DailyWeather implements Serializable {

    private Date forecastDate;
    private String weatherIconText;
    private String dayText;
    private String weatherText;
    private Double currentTemp;
    private Double minTemp;
    private Double maxTemp;
    private Double humidity;
    private Double pressure;
    private String cloudText;
    private String cloudDescription;
    private String iconText;
    private int windSpeed;
    private int windDirection;

    public DailyWeather(Date d, Double temp, Double tempMin, Double tempMax, Double humidity,
                        Double pressure, String cloudText, String cloudDescription, String iconText,
                        int windSpeed, int windDirection) {

        this.forecastDate = d;
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
    }

    public DailyWeather() {

    }

    //TODO Create Constructor

    public String getWeatherIconText() {
        return weatherIconText;
    }

    public String getDayText() {
        return dayText;
    }

    public String getWeatherText(){
        return weatherText;
    }

    public Double getCurrentTemp() {
        return currentTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }
}
