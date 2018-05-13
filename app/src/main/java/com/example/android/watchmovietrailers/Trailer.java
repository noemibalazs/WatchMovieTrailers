package com.example.android.watchmovietrailers;


import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {

    private String mTrailerImage;

    public Trailer(String trailer){
        mTrailerImage = trailer;
    }

    protected Trailer(Parcel in) {
        mTrailerImage = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getTrailerImage() {
        return mTrailerImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTrailerImage);
    }
}
