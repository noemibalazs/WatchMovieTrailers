package com.example.android.watchmovietrailers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import com.example.android.watchmovietrailers.utils.QueryReviews;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    private String mUrl;

    public ReviewLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        List<Review> reviews = QueryReviews.fetchDataFromJson(mUrl);
        return reviews;
    }
}
