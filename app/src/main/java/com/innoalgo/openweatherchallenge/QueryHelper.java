package com.innoalgo.openweatherchallenge;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryHelper {

    private static final String TAG = "QUERYUTILS";


    /**
     * Create a private constructor because no one should ever create a {@link QueryHelper} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryHelper() {
    }

    /**
     * Query the USGS dataset and return a List of {@link DailyWeather} objects to represent a single earthquake.
     */
    public static String fetchWeatherData(String requestUrl) {
        Log.d(TAG, "fetchWeatherData: ");
        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream", e);
        }
        return jsonResponse;
    }

    /**
     * Return a list of {@link DailyWeather} objects that has been built up from
     * parsing a JSON response.
     * @param requestUrl
     * @param context
     */
    public static ArrayList<DailyWeather> extractCurrentWeather(String requestUrl, Context context) {
        // Create an empty ArrayList that we can start adding DailyWeather to
        DailyWeather weather;

        // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject response = new JSONObject(fetchWeatherData(requestUrl));
            
            JSONArray weatherArray = response.getJSONArray("weather");//TODO Parse IAW API
            Log.d(TAG, weatherArray.toString());
            JSONObject coord = response.getJSONObject("coord");
            String base = response.getString("base");
            JSONObject main = response.getJSONObject("main");
            Double temp = main.getDouble("temp");
            Double tempMin = main.getDouble("temp_min");
            Double tempMax = main.getDouble("temp_max");
            Double humidity = main.getDouble("humidity");
            Double pressure = main.getDouble("pressure");

            JSONObject wind = response.getJSONObject("wind");
            JSONObject clouds = response.getJSONObject("clouds");
            Long time = response.getLong("dt");
            Log.d(TAG, "extractCurrentWeather: longtime " + time);
            Date d = new Date(time*1000);
            Log.d(TAG, "extractCurrentWeather: date " + d.toString());

            JSONObject system = response.getJSONObject("sys");

            JSONObject cloudDetails = weatherArray.getJSONObject(0);
            String cloudText = cloudDetails.getString("main");
            Log.d(TAG, "cloudText: " + cloudText);
            String cloudDescription = cloudDetails.getString("description");
            Log.d(TAG, "cloudDescription: " + cloudDescription);
            String icon = cloudDetails.getString("icon");
            Log.d(TAG, "iconString: " + icon);

            Double windSpeed = wind.getDouble("speed");
            int windDirection = wind.getInt("deg");



            weather = new DailyWeather(d, temp, tempMin, tempMax, humidity, pressure, cloudText,
                    cloudDescription, icon, windSpeed, windDirection, context);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
            return null;//TODO is this right?
        }

        // Return the list of weather
        ArrayList<DailyWeather> weathers = new ArrayList<>();
        weathers.add(weather);
        return weathers;
    }

    public static ArrayList<DailyWeather> extractForecastWeather(String requestUrl, Context context) {
        // Create an empty ArrayList that we can start adding DailyWeather to
        ArrayList<DailyWeather> weathers = new ArrayList<>();

        try {

            JSONObject response = new JSONObject(fetchWeatherData(requestUrl));

            JSONArray weatherArray = response.getJSONArray("list");
            for (int i = 0; i < weatherArray.length(); i++){
                JSONObject aDay = weatherArray.getJSONObject(i);

                Long time = aDay.getLong("dt");
                Log.d(TAG, "extractForecastWeather: long time" + time);
                Date d = new Date(time*1000);
                Log.d(TAG, "extractForecastWeather: Date:" + d.toString());

                JSONObject tempObject = aDay.getJSONObject("temp");
                Double temp = tempObject.getDouble("day");
                Double tempMin = tempObject.getDouble("min");
                Double tempMax = tempObject.getDouble("max");

                Double humidity = aDay.getDouble("humidity");
                Double pressure = aDay.getDouble("pressure");

                JSONArray cloudArray = aDay.getJSONArray("weather");
                JSONObject cloudDetails = cloudArray.getJSONObject(0);
                String cloudText = cloudDetails.getString("main");
                String cloudDescription = cloudDetails.getString("description");
                String icon = cloudDetails.getString("icon");

                Double windSpeed = aDay.getDouble("speed");
                int windDirection = aDay.getInt("deg");


                DailyWeather weather = new DailyWeather(d, temp, tempMin, tempMax, humidity, pressure, cloudText,
                        cloudDescription, icon, windSpeed, windDirection, context);
                Date today = new Date();

                //Request is returning Today in the response...
                //We don't want to have Today in the forecast list.
                if (d.getDay() - today.getDay() != 0){
                    weathers.add(weather);
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(TAG, "Problem parsing the earthquake JSON results", e);
            return null;//TODO is this right?
        }
        return weathers;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        Log.d(TAG, "createUrl: " + stringUrl);
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error with creating URL ", e);
        }
        return url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        Log.d(TAG, "makeHttpRequest: ");
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static byte[] imageByter(Context ctx, String strurl) {
        try {
            URL url = new URL(strurl);
            InputStream is = (InputStream) url.getContent();
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = is.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}