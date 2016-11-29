package com.innoalgo.openweatherchallenge;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Corey on 11/29/2016.
 */

public class DailyWeather implements Serializable {

    private Date date;
    private String dayText;
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

    public DailyWeather(Date d, Double temp, Double tempMin, Double tempMax, Double humidity,
                        Double pressure, String cloudText, String cloudDescription, String iconText,
                        Double windSpeed, int windDirection) {

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
    }

    public DailyWeather() {

    }

    //TODO Create Constructor

    public String getWeatherIconText() {
        return iconText;
    }

    public String getDayText() {
        return dayText;
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
}
