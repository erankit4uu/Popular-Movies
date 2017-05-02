package com.example.ankitchaturvedi.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ankitchaturvedi.popularmovies.Database.PopularMovieContract.PopularMovieEntry;

/**
 * Created by Ankit.Chaturvedi on 19-Apr-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "popular_movie.db";

    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + PopularMovieEntry.TABLE_NAME + " (" +
                PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopularMovieEntry.COL_MOVIE_ID + " INTEGER NOT NULL," +
                PopularMovieEntry.COL_TITLE + " TEXT NOT NULL, " +
                PopularMovieEntry.COL_POSTER_IMAGE + " TEXT NOT NULL, " +
                PopularMovieEntry.COL_OVERVIEW + " TEXT NOT NULL, " +
                PopularMovieEntry.COL_RATINGS + " REAL NOT NULL, " +
                PopularMovieEntry.COL_RELEASE_DATE + " TEXT NOT NULL, "+
                PopularMovieEntry.COL_BACKDROP_IMAGE + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP_TABLE_IF_EXISTS" + PopularMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
