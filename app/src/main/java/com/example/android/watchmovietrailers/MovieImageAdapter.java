package com.example.android.watchmovietrailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieImageAdapter extends ArrayAdapter<Movie> {

    public MovieImageAdapter(@NonNull Context context, ArrayList<Movie> movie) {
        super(context, 0, movie);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, parent, false);
        }

        Movie movie = getItem(position);

        ImageView picture = listView.findViewById(R.id.iv_image);
        Picasso.with(getContext())
                .load(movie.getPosterImage())
                .into(picture);

        return listView;
    }
}
