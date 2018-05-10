package com.example.android.watchmovietrailers;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.example.android.watchmovietrailers.utils.QueryTrailers;

import java.util.List;

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>>{

    private String mUrl;

    public TrailerLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<Trailer> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        List<Trailer> trailers = QueryTrailers.fetchDataFromJson(mUrl);
        return trailers;
    }
}
