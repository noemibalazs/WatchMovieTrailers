package com.example.android.watchmovietrailers.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.watchmovietrailers.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String MOVIE_DB = "movie.db";
    private static final int MOVIE_VERSION = 1;
    private static final String PRAGMA_FOREIGN_KEYS_ON = "PRAGMA foreign_keys=ON; ";

    public MovieDbHelper(Context context) {
        super(context, MOVIE_DB, null, MOVIE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.MOVIE_TABLE + " ("
                + MovieEntry.ID + " INTEGER NOT NULL PRIMARY KEY UNIQUE, "
                + MovieEntry.MOVIE_TITLE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_DESCRIPTION + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_USER_RATE + " TEXT NOT NULL, "
                + MovieEntry.MOVIE_IMAGE + " TEXT NOT NULL ); ";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(PRAGMA_FOREIGN_KEYS_ON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.MOVIE_TABLE);
        onCreate(db);

    }
}
