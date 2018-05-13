package com.example.android.watchmovietrailers;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieInfo implements Parcelable {

    private String mTitle;
    private String mDescription;
    private String mUserRate;
    private String mReleaseDate;
    private String mImageUrl;

     private List<Trailer> mTrailers;
     private List<Review> mReviews;

    public MovieInfo(String title, String description, String userRate, String releaseDate, String imageUrl){
        mTitle = title;
        mDescription = description;
        mUserRate = userRate;
        mReleaseDate = releaseDate;
        mImageUrl = imageUrl;
        mTrailers = new ArrayList<>();
        mReviews = new ArrayList<>();
    }

    private MovieInfo(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mUserRate = in.readString();
        mReleaseDate = in.readString();
        mImageUrl = in.readString();
        mTrailers = in.createTypedArrayList(Trailer.CREATOR);
        mReviews = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getMovieTitle(){
        return mTitle;
    }

    public String getMovieDescription(){
        return mDescription;
    }

    public String getRate(){
        return mUserRate;
    }

    public String getMovieReleaseDate(){
        return mReleaseDate;
    }

   public String getMovieImage(){
        return mImageUrl;
   }

   public List<Trailer> getTrailers(){
        return mTrailers;
   }

   public List<Review> getReviews(){
        return mReviews;
   }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mUserRate);
        dest.writeString(mReleaseDate);
        dest.writeString(mImageUrl);
        dest.writeTypedList(mTrailers);
        dest.writeTypedList(mReviews);
    }
}
