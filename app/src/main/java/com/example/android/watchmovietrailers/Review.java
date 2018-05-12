package com.example.android.watchmovietrailers;


public class Review {

    private String mAuthor;
    private String mComments;

    public Review(String author, String comments){
        mAuthor=author;
        mComments=comments;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getComments(){
        return mComments;
    }
}
