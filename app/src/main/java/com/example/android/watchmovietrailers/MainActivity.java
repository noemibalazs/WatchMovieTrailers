package com.example.android.watchmovietrailers;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> , SharedPreferences.OnSharedPreferenceChangeListener{

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private MovieImageAdapter mAdapter;
    private static final int LOADER_ID = 9;
    private String link;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.grid_view);
        mAdapter= new MovieImageAdapter(this, new ArrayList<Movie>());
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = mAdapter.getItem(position);

                String title = movie.getOriginalTitle();
                String rate = movie.getUserRating();
                String overview = movie.getMovieOverview();
                String date = movie.getReleaseDate();
                String image = movie.getPosterImage();
                int idImage = movie.getId();

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Overview", overview);
                intent.putExtra("Release", date);
                intent.putExtra("Rate", rate);
                intent.putExtra("Image", image);
                intent.putExtra("ID", idImage);
                startActivity(intent);
            }
        });

        mProgressBar = findViewById(R.id.progress_bar);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings_menu){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPreferences.getString(getString(R.string.order_by_label_key),
                getString(R.string.order_by_label_popularity_value));


        link = "https://api.themoviedb.org/3/movie/";

        Uri uri = Uri.parse(link);
        Uri.Builder builder = uri.buildUpon();

        builder.appendPath(orderBy);
        builder.appendQueryParameter("api_key", "e4ec57629fb398e143f46a5eddae08f8");

        return new MovieLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {

        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();

        if (data!= null && !data.isEmpty()){
            mAdapter.addAll(data);
        } else {
            Toast.makeText(this, "Sorry, no movie image!", Toast.LENGTH_SHORT).show();
        } if (!isChecked()){
            Toast.makeText(this, "Sorry, no internet connection!", Toast.LENGTH_LONG).show();
        } else {
            Log.v(TAG, "Network is available");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.clear();
    }

    public boolean isChecked(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.order_by_label_key))){
            mAdapter.clear();
            mProgressBar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }

    }
}
