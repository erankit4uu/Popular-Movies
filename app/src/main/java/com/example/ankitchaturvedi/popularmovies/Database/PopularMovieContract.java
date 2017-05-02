package com.example.ankitchaturvedi.popularmovies.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Ankit.Chaturvedi on 19-Apr-17.
 */

public class PopularMovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.ankitchaturvedi.popularmovies";

    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String FAVOURITE_MOVIE = "favourite_movie";

    public static final class PopularMovieEntry implements BaseColumns {

        public static final Uri BUILD_CONTENT = CONTENT_URI.buildUpon().appendPath(FAVOURITE_MOVIE).build();
        public static final String TABLE_NAME = "favourite_movie";

        public static final String COL_MOVIE_ID = "movie_id";

        public static final String COL_TITLE = "title";
        public static final String COL_RATINGS = "ratings";
        public static final String COL_RELEASE_DATE = "release_date";
        public static final String COL_POSTER_IMAGE = "image";
        public static final String COL_OVERVIEW = "overview";
        public static final String COL_BACKDROP_IMAGE = "backdrop_image";


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FAVOURITE_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + FAVOURITE_MOVIE;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }


}
