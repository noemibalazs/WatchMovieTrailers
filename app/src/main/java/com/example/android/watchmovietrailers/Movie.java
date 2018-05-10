package com.example.android.watchmovietrailers;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

    private String mOriginalTitle;
    private String mImageThumbnail;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;
    private int mId;

    public Movie(String originalTitle, String imageThumbnail, String overView,  String userRating, String releaseDate, int id){
        mOriginalTitle = originalTitle;
        mImageThumbnail = imageThumbnail;
        mOverview = overView;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mId = id;
    }


    public String getOriginalTitle(){
        return mOriginalTitle;
    }

    public String getPosterImage(){
        return mImageThumbnail;
    }

    public String getMovieOverview(){
        return mOverview;
    }

    public String getUserRating(){
        return  mUserRating;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public int getId(){
        return  mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mOriginalTitle);
        dest.writeString(mImageThumbnail);
        dest.writeString(mOverview);
        dest.writeString(mUserRating);
        dest.writeString(mReleaseDate);
        dest.writeInt(mId);

    }

    private Movie (Parcel in){
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mImageThumbnail = in.readString();
        mUserRating = in.readString();
        mReleaseDate = in.readString();
        mId = in.readInt();
    }


    public final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
