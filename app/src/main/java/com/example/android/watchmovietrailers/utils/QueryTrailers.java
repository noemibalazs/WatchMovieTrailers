package com.example.android.watchmovietrailers.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.watchmovietrailers.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryTrailers {

    private static final String TAG = QueryTrailers.class.getSimpleName();
    private static final String TRAILER_RESULTS = "results";
    private static final String TRAILER_KEY = "key";
    private static final String YOUTUBE_PATH = "https://www.youtube.com/watch?v=";

    public QueryTrailers(){}

    public static List<Trailer> fetchDataFromJson(String json){

        URL url = createUrl(json);
        String responseJson = "";
        try {
            responseJson = makeHttpRequest(url);
        } catch (IOException e){
            e.printStackTrace();
            Log.v(TAG, "Error making HTTP request" + e);
        }
        List<Trailer> trailer = extractDataFromJson(responseJson);
        return trailer;
    }

    private static URL createUrl(String json) {
        URL url = null;
        try {
            url = new URL(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(TAG, "Error building URL" + e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String string = "";
        if (url == null){
            return  string;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                string = readFromStream(inputStream);
            } else {
                Log.v(TAG, "Error getting response code" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "Error retrieving trailers " + e);
        } finally {
            if (httpURLConnection!= null){
                httpURLConnection.disconnect();
            } if (inputStream != null){
                inputStream.close();
            }
        }
        return string;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null){
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Trailer> extractDataFromJson(String js){
        if (TextUtils.isEmpty(js)){
            return null;
        }
        List<Trailer> trailers = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(js);
            JSONArray resultArray = root.getJSONArray(TRAILER_RESULTS);

            for (int i=0; i<resultArray.length(); i++){

                JSONObject inside = resultArray.getJSONObject(i);

                String keyPath = inside.getString(TRAILER_KEY);
                String key = YOUTUBE_PATH + keyPath;

                Trailer trailer = new Trailer(key);
                trailers.add(trailer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;

    }
}
