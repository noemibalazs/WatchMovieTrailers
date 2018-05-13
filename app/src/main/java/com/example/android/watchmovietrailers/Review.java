package com.example.android.watchmovietrailers;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    private String mAuthor;
    private String mComments;

    public Review(String author, String comments){
        mAuthor=author;
        mComments=comments;
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthor(){
        return mAuthor;
    }

    public String getComments(){
        return mComments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mComments);
    }

    private Review (Parcel in){
        mAuthor = in.readString();
        mComments = in.readString();
    }


}
