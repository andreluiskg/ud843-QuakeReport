package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.getJSONArray("features");
            JSONObject jsonQuake;
            JSONObject jsonQuakeProperties;

            for (int i = 0; i < features.length(); i++) {
                jsonQuake = features.getJSONObject(i);
                jsonQuakeProperties = jsonQuake.getJSONObject("properties");

                earthquakes.add(new Earthquake(
                        jsonQuakeProperties.getDouble("mag"),
                        jsonQuakeProperties.getString("place"),
                        jsonQuakeProperties.getLong("time"),
                        jsonQuakeProperties.getString("url")));

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl) {

        String jsonResponse = null;

        try {
            URL url = new URL(requestUrl);
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Earthquake> earthquakes = extractEarthquakes(jsonResponse);

        return earthquakes;

    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(999999999);
            urlConnection.setReadTimeout(999999999);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "urlConnection com problemas");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

}