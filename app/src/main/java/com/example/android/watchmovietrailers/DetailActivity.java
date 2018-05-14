package com.example.android.watchmovietrailers;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mUserRate;
    private ImageView mImage;

    private String link;

    private RecyclerView mReviewRecycle;
    private ReviewRecAdapter mReviewAdapter;

    private RecyclerView mTrailerRecycler;
    private TrailerRecAdapter mTrailerAdapter;

    private static final int REVIEW_LOADER = 7;
    private static final int TRAILER_LOADER = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = findViewById(R.id.tv_title);
        mOverview = findViewById(R.id.tv_detail_description);
        mReleaseDate = findViewById(R.id.tv_detail_release_date);
        mUserRate = findViewById(R.id.tv_detail_user_rating);
        mImage = findViewById(R.id.iv_detail_image);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(REVIEW_LOADER, null, this);

        LoaderManager manager = getLoaderManager();
        manager.initLoader(TRAILER_LOADER, null, this);

        mReviewRecycle = findViewById(R.id.recycle_review);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecycle.setLayoutManager(layoutManager);
        mReviewRecycle.setHasFixedSize(true);

        mReviewAdapter = new ReviewRecAdapter(this);
        mReviewRecycle.setAdapter(mReviewAdapter);

        mTrailerRecycler = findViewById(R.id.recycle_trailer);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTrailerRecycler.setLayoutManager(layout);
        mTrailerRecycler.setHasFixedSize(true);

        mTrailerAdapter = new TrailerRecAdapter(this);
        mTrailerRecycler.setAdapter(mTrailerAdapter);



        Intent intent = getIntent();
        if (intent == null){
            finish();
            Toast.makeText(this, "Error getting details", Toast.LENGTH_LONG).show();
        }

        mTitle.setText(intent.getExtras().getString("Title"));
        mOverview.setText(intent.getExtras().getString("Description"));
        mReleaseDate.setText(intent.getExtras().getString("Date"));
        mUserRate.setText(intent.getExtras().getString("Rate"));

        Picasso.with(this).load(intent.getExtras().getString("Image")).into(mImage);


    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        if (id == REVIEW_LOADER){

        link = "https://api.themoviedb.org/3/movie/";

        Uri uri = Uri.parse(link);
        Uri.Builder builder = uri.buildUpon();

        builder.appendPath(String.valueOf(getIntent().getExtras().getInt("ID")))
                .appendPath("reviews")
                .appendQueryParameter("api_key", "e4ec57629fb398e143f46a5eddae08f8");

        return new ReviewLoader(this, builder.toString());

        }

        else if (id == TRAILER_LOADER){

            link = "https://api.themoviedb.org/3/movie/";

            Uri uri = Uri.parse(link);
            Uri.Builder builder = uri.buildUpon();

            builder.appendPath(String.valueOf(getIntent().getExtras().getInt("ID")))
                    .appendPath("videos")
                    .appendQueryParameter("api_key", "e4ec57629fb398e143f46a5eddae08f8");

            return new TrailerLoader(this, builder.toString());

        }
        return null;

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        int id = loader.getId();

        if (id == REVIEW_LOADER){
           List<Review> reviews = (List<Review>)data;
           if (reviews!=null && !reviews.isEmpty()){
              mReviewAdapter.bindReviewList(reviews);
           } else {
              Toast.makeText(this, "Sorry, no reviews", Toast.LENGTH_SHORT).show();
           }
        }

        else if (id == TRAILER_LOADER){
            List<Trailer> trailers = (List<Trailer>) data;
            if (trailers!=null && !trailers.isEmpty()){
                mTrailerAdapter.bindTrailerList(trailers);
            } else {
                Toast.makeText(this, "Sorry, no trailers", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {
        int id = loader.getId();
        if (id == TRAILER_LOADER){
        mTrailerAdapter.bindTrailerList(null);}
        else if (id == REVIEW_LOADER){
        mReviewAdapter.bindReviewList(null);}
    }


}

