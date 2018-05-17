package com.example.android.watchmovietrailers.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.watchmovietrailers.data.MovieContract.MovieEntry;

public class MovieProvider extends ContentProvider {

    private static final String TAG = MovieProvider.class.getSimpleName();

    private MovieDbHelper mDbHelper;

    private static final int MOVIES = 111;
    private static final int MOVIE_ITEM = 333;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.MOVIE_PATH, MOVIES );
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.MOVIE_PATH + "/#", MOVIE_ITEM);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match){
                case MOVIES:
                    cursor = db.query(MovieEntry.MOVIE_TABLE, projection, selection, selectionArgs,  null, null, sortOrder);
                    break;
                case MOVIE_ITEM:
                    selection = MovieEntry.MOVIE_ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    cursor = db.query(MovieEntry.MOVIE_TABLE, projection, selection, selectionArgs, null,null, sortOrder);
                    break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

       long id = 0;
        switch (match){
            case MOVIES:
                id = db.insert(MovieEntry.MOVIE_TABLE, null, values);
                break;
            default:
                throw  new IllegalArgumentException("Insertion is not supported for " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowDeleted;
        switch (match){

            case MOVIES:
            rowDeleted = db.delete(MovieEntry.MOVIE_TABLE,selection, selectionArgs);
            if (rowDeleted!=0){
                getContext().getContentResolver().notifyChange(uri, null);
            } return rowDeleted;

            case MOVIE_ITEM:
                selection = MovieEntry.MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted = db.delete(MovieEntry.MOVIE_TABLE, selection, selectionArgs);
                if (rowDeleted!=0){
                    getContext().getContentResolver().notifyChange(uri, null);
                } return rowDeleted;
            default:
                throw  new IllegalArgumentException("Cannot delete row for " + uri);

        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match =  sUriMatcher.match(uri);
        int updateRow;
        switch (match){
            case MOVIES:
                updateRow = db.update(MovieEntry.MOVIE_TABLE, values, selection, selectionArgs);
                if (updateRow!=0){
                    getContext().getContentResolver().notifyChange(uri, null);
                } return updateRow;

            case MOVIE_ITEM:
                selection = MovieEntry.MOVIE_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                updateRow = db.update(MovieEntry.MOVIE_TABLE, values, selection, selectionArgs);
                if (updateRow!=0){
                    getContext().getContentResolver().notifyChange(uri, null);
                } return updateRow;
            default:
                throw  new IllegalArgumentException("Cannot delete row for" + uri);
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIES:
                return MovieEntry.DYR_TYPE;
            case MOVIE_ITEM:
                return MovieEntry.ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri "+ uri + "with match" + match);
        }
    }
}
