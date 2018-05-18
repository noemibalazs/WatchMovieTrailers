package com.example.android.watchmovietrailers;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.watchmovietrailers.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

public class FavoriteDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView mTitle;
    private TextView mDescription;
    private TextView mUserRate;
    private TextView mReleaseDate;
    private ImageView mPicture;
    private Uri mCurrentUri;

    private static final int LOADER_ID = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = findViewById(R.id.favorite_detail_title);
        mDescription = findViewById(R.id.favorite_detail_description);
        mUserRate = findViewById(R.id.favorite_detail_user_rating);
        mReleaseDate = findViewById(R.id.favorite_detail_release_date);
        mPicture = findViewById(R.id.favorite_detail_image);

        Intent intent = getIntent();
        mCurrentUri = intent.getData();

        LoaderManager manager = getLoaderManager();
        manager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = new String[]{
                MovieEntry.ID,
                MovieEntry.MOVIE_TITLE,
                MovieEntry.MOVIE_DESCRIPTION,
                MovieEntry.MOVIE_RELEASE_DATE,
                MovieEntry.MOVIE_USER_RATE,
                MovieEntry.MOVIE_IMAGE
        };

        return new CursorLoader(this, mCurrentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount()<1){
            return;
        }
        if (cursor.moveToFirst()){

            int titleIndex = cursor.getColumnIndex(MovieEntry.MOVIE_TITLE);
            int descriptionIndex = cursor.getColumnIndexOrThrow(MovieEntry.MOVIE_DESCRIPTION);
            int userIndex = cursor.getColumnIndex(MovieEntry.MOVIE_USER_RATE);
            int dateIndex = cursor.getColumnIndex(MovieEntry.MOVIE_RELEASE_DATE);
            int imageIndex = cursor.getColumnIndexOrThrow(MovieEntry.MOVIE_IMAGE);

            String title = cursor.getString(titleIndex);
            String description = cursor.getString(descriptionIndex);
            String userRate = cursor.getString(userIndex);
            String date = cursor.getString(dateIndex);
            String image = cursor.getString(imageIndex);

            mTitle.setText(title);
            mDescription.setText(description);
            mUserRate.setText(userRate);
            mReleaseDate.setText(date);
            Picasso.with(this).load(image).into(mPicture);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
