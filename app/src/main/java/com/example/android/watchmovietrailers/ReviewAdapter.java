package com.example.android.watchmovietrailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, ArrayList<Review> reviews){
        super(context, 0, reviews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, parent, false);
        }

        Review review = getItem(position);

        TextView author = listView.findViewById(R.id.tv_author);
        author.setText(review.getAuthor());

        TextView comments = listView.findViewById(R.id.tv_comment);
        comments.setText(review.getComments());

        return listView;
    }
}
