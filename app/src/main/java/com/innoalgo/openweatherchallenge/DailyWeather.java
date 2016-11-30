package com.innoalgo.openweatherchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private String bitmapDirectory;


    private String TAG = "DailyWeather: ";

    public DailyWeather(Date d, Double temp, Double tempMin, Double tempMax, Double humidity,
                        Double pressure, String cloudText, String cloudDescription, String iconText,
                        Double windSpeed, int windDirection, Context context) {
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

        File icon = new File(context.getFilesDir(), this.iconText + ".png");

        if (!icon.exists()) {
            Bitmap bitmap = downloadBitmap(context);
            writeBitmapToFile(context, bitmap);
        }
    }


    public String getWeatherIconText() {
        return iconText;
    }

    public String getDayOfWeek() {
        Date today = new Date();
        today.setHours(1);
        today.setMinutes(0);
        today.setSeconds(0);
        Log.d(TAG, "getDayOfWeek: \n" + date.toString() + "\n" + today.toString());
        if (date.getDay() - today.getDay() == 0) {
            return "Today";
        } else if (date.getDay() - today.getDay() == 1
                || date.getDay() - today.getDay() == -6) {
            return "Tomorrow";
        } else {
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

    public Bitmap getWeatherIcon(Context c) {
        File icon = new File(c.getFilesDir(), this.iconText + ".png");
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(icon.getAbsolutePath(), bmOptions);
        return bitmap;
    }

    private Bitmap downloadBitmap(Context c){
        String mUrl = "http://openweathermap.org/img/w/";
        byte[] mBytes = QueryHelper.imageByter(c, mUrl + this.iconText + ".png");
        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes, 0, mBytes.length);
        return bitmap;
    }

    private void writeBitmapToFile(Context c, Bitmap bitmap) {
        FileOutputStream out = null;
        File icon = new File(c.getFilesDir(), this.iconText + ".png");
        try {
            out = new FileOutputStream(icon.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Double getHumidity() {
        return humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }
}
