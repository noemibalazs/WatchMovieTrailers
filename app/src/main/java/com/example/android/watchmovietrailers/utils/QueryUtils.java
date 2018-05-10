package com.example.android.watchmovietrailers.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.watchmovietrailers.Movie;

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

public class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();
    public QueryUtils(){}

    private static final String MOVIE_RESULTS = "results";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_POSTER_PATH = "poster_path";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_ID = "id";


    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w185";

    public static List<Movie> fetchDataFromJson(String requestString){

        URL url = createUrl(requestString);
        String jsonRequest = null;
        try {
            jsonRequest = makeHttpRequest(url);
        }catch (IOException e){
            Log.v(TAG, "Error making HTTP request" + e);
        }

        List<Movie> movies = extractDataFromJson(jsonRequest);
        return movies;
    }

    private static URL createUrl (String string){
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e){
            Log.v(TAG, "Error building url" + e);
        }
        return  url;
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
        }
        catch (IOException e){
            Log.v(TAG, "Error retrieving Movie results" + e);
        } finally {
            if (urlConnection!= null){
                urlConnection.disconnect();
            } if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder builder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader( inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
           String line = reader.readLine();
           while (line!=null){
               builder.append(line);
               line = reader.readLine();
           }
        }
        return builder.toString();
    }

    private static List<Movie> extractDataFromJson (String movieJson) {
        if (TextUtils.isEmpty(movieJson)){
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        try{
            JSONObject rootJson = new JSONObject(movieJson);
            JSONArray resultsArray = rootJson.getJSONArray(MOVIE_RESULTS);

            for (int i=0; i<resultsArray.length(); i++){

                JSONObject jsonObject = resultsArray.getJSONObject(i);

                String title = jsonObject.getString(MOVIE_TITLE);
                String voteAverage = jsonObject.getString(MOVIE_VOTE_AVERAGE);

                String posterPath = jsonObject.getString(MOVIE_POSTER_PATH);
                String poster = POSTER_URL + posterPath;

                String overview = jsonObject.getString(MOVIE_OVERVIEW);
                String releaseDate = jsonObject.getString(MOVIE_RELEASE_DATE);

                int id = jsonObject.getInt(MOVIE_ID);

                Movie movie = new Movie(title, poster, overview, voteAverage, releaseDate, id);
                movieList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;

    }

}

