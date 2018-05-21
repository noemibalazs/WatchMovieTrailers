package com.example.android.watchmovietrailers.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class MovieContract {

    public MovieContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.watchmovietrailers";
    public static final String MOVIE_PATH = "movies";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, MOVIE_PATH);

        public static final String MOVIE_TABLE = "movie_table";

        public static final String DYR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;
        public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + MOVIE_PATH;

        public static final String ID = BaseColumns._ID;
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_DESCRIPTION = "description";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_USER_RATE= "rate";
        public static final String MOVIE_IMAGE = "image";

    }


}
