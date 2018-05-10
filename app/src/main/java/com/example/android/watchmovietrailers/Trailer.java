package com.example.android.watchmovietrailers;

import android.net.Uri;

public class Trailer {

    private String mTrailerImage;

    public Trailer(String trailer){
        mTrailerImage = trailer;
    }

    public String getTrailerImage() {
        return mTrailerImage;
    }


}
