package com.example.android.watchmovietrailers;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.android.watchmovietrailers.data.MovieContract.MovieEntry;
import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends CursorAdapter {

    public FavoriteAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView picture = view.findViewById(R.id.iv_image);
        int index = cursor.getColumnIndex(MovieEntry.MOVIE_IMAGE);
        String image = cursor.getString(index);
        Picasso.with(context).load(image).into(picture);
    }
}
