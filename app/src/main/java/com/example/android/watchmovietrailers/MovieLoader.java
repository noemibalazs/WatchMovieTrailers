package com.example.android.watchmovietrailers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import com.example.android.watchmovietrailers.utils.QueryUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mUrl;

    public MovieLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
      forceLoad();
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        List<Movie> movie = QueryUtils.fetchDataFromJson(mUrl);
        return movie;
    }
}
