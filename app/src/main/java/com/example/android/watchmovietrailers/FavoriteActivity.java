package com.example.android.watchmovietrailers;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.watchmovietrailers.data.MovieContract;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private GridView mGridView;
    private FavoriteAdapter mAdapter;
    private static final int LOADER_ID = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mGridView = findViewById(R.id.grid);
        mAdapter = new FavoriteAdapter(this, null);
        mGridView.setAdapter(mAdapter);

        LoaderManager manager = getLoaderManager();
        manager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = new String[]{
                MovieContract.MovieEntry.ID,
                MovieContract.MovieEntry.MOVIE_IMAGE};
        return new CursorLoader(FavoriteActivity.this, MovieContract.MovieEntry.CONTENT_URI, projection, null, null, null );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}

