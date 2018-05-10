package com.example.android.watchmovietrailers.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.watchmovietrailers.Review;

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

public class QueryReviews {

    private static final String TAG = QueryReviews.class.getSimpleName();

    private static final String REVIEW_RESULTS = "results";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";


    public QueryReviews(){}

    public static List<Review> fetchDataFromJson(String json){

        URL url = createUrl(json);
        String responseJson = "";
        try {
            responseJson = makeHttpRequest(url);
        }catch (IOException e){
            e.printStackTrace();
            Log.v(TAG, "Error making HTTP request" + e);
        }
        List<Review> reviews = extractDataFromJson(responseJson);
        return  reviews;
    }

    private static URL createUrl(String string){
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.v(TAG, "Error building url" + e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v(TAG, "Error getting response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            e.printStackTrace();
            Log.v(TAG, "Error retrieving reviews" + e);
        }
        finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }if (inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder builder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return  builder.toString();
    }

    private static List<Review> extractDataFromJson(String reviewJson) {
        if (TextUtils.isEmpty(reviewJson)){
            return null;
        }

        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(reviewJson);
            JSONArray resultArray = root.getJSONArray(REVIEW_RESULTS);

            for (int i=0; i<resultArray.length(); i++){
                JSONObject insideObject = resultArray.getJSONObject(i);
                String author = insideObject.getString(REVIEW_AUTHOR);
                String comment = insideObject.getString(REVIEW_CONTENT);

                Review rev = new Review(author, comment);
                reviews.add(rev);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
