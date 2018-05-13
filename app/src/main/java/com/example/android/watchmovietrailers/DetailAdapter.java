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
import java.util.List;

public class DetailAdapter extends ArrayAdapter<MovieInfo> {

    public DetailAdapter(Context context, ArrayList<MovieInfo> objects) {
        super(context, 0, objects);
    }

    MovieInfo mMovieInfo;

    private static final int MOVIE_TYPE = 0;
    private static final int TRAILER_TYPE = 1;
    private static final int REVIEW_TYPE = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MOVIE_TYPE;
        } else if (position < 1 + mMovieInfo.getTrailers().size()) {
            return TRAILER_TYPE;
        } else if (position >= 1 + mMovieInfo.getTrailers().size()) {
            return REVIEW_TYPE;
        } else
            return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        int viewType = getItemViewType(position);
        switch (viewType) {
            case MOVIE_TYPE:
                MovieViewHolder movieViewHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_detail, null);
                    movieViewHolder = new MovieViewHolder(convertView);
                    convertView.setTag(movieViewHolder);
                } else
                    movieViewHolder = (MovieViewHolder) convertView.getTag();
                    movieViewHolder.bindMovieDetail(mMovieInfo);

                break;

            case TRAILER_TYPE:
                TrailerViewHolder trailerViewHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, null);
                    trailerViewHolder = new TrailerViewHolder(convertView);
                    convertView.setTag(trailerViewHolder);
                } else
                    trailerViewHolder = (TrailerViewHolder) convertView.getTag();
                     trailerViewHolder.bindTrailerData(mMovieInfo.getTrailers().get(TRAILER_TYPE));

                break;


            case REVIEW_TYPE:
                ReviewViewHolder reviewViewHolder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, null);
                    reviewViewHolder = new ReviewViewHolder(convertView);
                    convertView.setTag(reviewViewHolder);
                } else
                    reviewViewHolder = (ReviewViewHolder) convertView.getTag();
                    reviewViewHolder.bindReviewData(mMovieInfo.getReviews().get(REVIEW_TYPE));

                break;
        }
        return convertView;
    }

    class MovieViewHolder {

        TextView mTitle;
        TextView mDescription;
        TextView mReleaseDate;
        TextView mUserRate;
        ImageView mImage;

        public MovieViewHolder(View view) {
            mTitle = view.findViewById(R.id.tv_title);
            mDescription = view.findViewById(R.id.tv_detail_description);
            mReleaseDate = view.findViewById(R.id.tv_detail_release_date);
            mUserRate = view.findViewById(R.id.tv_detail_user_rating);
            mImage = view.findViewById(R.id.iv_detail_image);
        }

        public void bindMovieDetail(MovieInfo movieInfo){
            mTitle.setText(movieInfo.getMovieTitle());
            mDescription.setText(movieInfo.getMovieDescription());
            mReleaseDate.setText(movieInfo.getMovieReleaseDate());
            mUserRate.setText(movieInfo.getRate());
            Picasso.with(getContext()).load(movieInfo.getMovieImage()).into(mImage);

        }
    }


    class TrailerViewHolder {

        ImageView mImageArrow;

        public TrailerViewHolder(View view) {
            mImageArrow = view.findViewById(R.id.image_play);

        }

        public void bindTrailerData(final Trailer trailer){
            mImageArrow.setImageResource(R.drawable.play);
            mImageArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(trailer.getTrailerImage()));
                    getContext().startActivity(intent);
                }
            });
        }

    }

    class ReviewViewHolder {

        TextView mAuthor;
        TextView mComment;

        public ReviewViewHolder(View view) {
            mAuthor = view.findViewById(R.id.tv_author);
            mComment = view.findViewById(R.id.tv_comment);
        }

        public void bindReviewData(Review review){
            mAuthor.setText(review.getAuthor());
            mComment.setText(review.getComments());
        }


    }

}





