package com.example.android.watchmovietrailers;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.watchmovietrailers.data.MovieContract.MovieEntry;


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
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavoriteActivity.this, FavoriteDetailActivity.class);
                Uri uri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);
                intent.setData(uri);
                startActivity(intent);
            }
        });

        LoaderManager manager = getLoaderManager();
        manager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projection = new String[]{
                MovieEntry.ID,
                MovieEntry.MOVIE_IMAGE};
        return new CursorLoader(FavoriteActivity.this, MovieEntry.CONTENT_URI, projection, null, null, null );
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

