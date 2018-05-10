package com.example.android.watchmovietrailers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends ArrayAdapter<Trailer> {

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers){
        super(context, 0, trailers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, parent, false);
        }

        final Trailer trailer = getItem(position);


        ImageView imageView = listView.findViewById(R.id.image_play);
        imageView.setImageResource(R.drawable.play);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getTrailerImage()));
                getContext().startActivity(intent);

            }
        });


        return listView;
    }
}
