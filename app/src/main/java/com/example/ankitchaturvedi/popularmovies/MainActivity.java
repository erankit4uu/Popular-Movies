package com.example.ankitchaturvedi.popularmovies;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ankitchaturvedi.popularmovies.Adapter.MovieDataAdapter;
import com.example.ankitchaturvedi.popularmovies.Interface.ApiInterface;
import com.example.ankitchaturvedi.popularmovies.Models.Movie;
import com.example.ankitchaturvedi.popularmovies.Models.MovieData;
import com.example.ankitchaturvedi.popularmovies.utils.ApiClient;
import com.example.ankitchaturvedi.popularmovies.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.ankitchaturvedi.popularmovies.Database.PopularMovieContract.PopularMovieEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List> {

    RecyclerView mrecycler_view;
    ProgressBar progressBar;
    BottomNavigationView mbottom_navigation;
    public static List<Movie> movies=new ArrayList<Movie>();
    MovieDataAdapter mMovieDataAdapter;
    LinearLayoutManager layoutManager;
    Parcelable listState;
    final private String LIST_KEY="Recycler_state";

    private static final String[] FAV_MOVIE_COLUMNS = {
            PopularMovieEntry.TABLE_NAME + "." + PopularMovieEntry._ID,
            PopularMovieEntry.COL_MOVIE_ID,
            PopularMovieEntry.COL_TITLE,
            PopularMovieEntry.COL_POSTER_IMAGE,
            PopularMovieEntry.COL_OVERVIEW,
            PopularMovieEntry.COL_RATINGS,
            PopularMovieEntry.COL_RELEASE_DATE,
            PopularMovieEntry.COL_BACKDROP_IMAGE,
    };
    static final int COL_MOVIE_ID = 1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER_IMAGE = 3;
    static final int COL_OVERVIEW = 4;
    static final int COL_RATING = 5;
    static final int COL_RELEASE_DATE = 6;
    static final int COL_BACKDROP_IMAGE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);


        mrecycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mbottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mrecycler_view.setLayoutManager(layoutManager);
        getSupportLoaderManager().initLoader(0, null, this);
        getPopularData();
        listState = mrecycler_view.getLayoutManager().onSaveInstanceState();





        int PERMISSION = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!CheckPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION);


            mbottom_navigation.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.top_rated:
                                    progressBar.setVisibility(View.VISIBLE);
                                    mrecycler_view.setVisibility(View.GONE);
                                    getTopRatedData();
                                    break;
                                case R.id.popular:
                                    progressBar.setVisibility(View.VISIBLE);
                                    mrecycler_view.setVisibility(View.GONE);
                                    getPopularData();
                                    break;
                                case R.id.favourite:
                                    getSupportLoaderManager().getLoader(0).forceLoad();
                                    break;
                            }

                            return true;
                        }
                    });

        }
    }

    public static boolean CheckPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void getTopRatedData() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieData> call = apiService.getTopRatedMovies(Constant.api_key);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                movies = response.body().getResults();
                mMovieDataAdapter = new MovieDataAdapter(MainActivity.this, movies);
                mrecycler_view.setAdapter(mMovieDataAdapter);
                mMovieDataAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mrecycler_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                // Log error here since request failed
                Log.e("RESPONSE", t.toString());
            }
        });
    }

    private void getPopularData() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieData> call = apiService.getPopularMovies(Constant.api_key);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                movies = response.body().getResults();
                mMovieDataAdapter = new MovieDataAdapter(MainActivity.this, movies);
                mrecycler_view.setAdapter(mMovieDataAdapter);
                mMovieDataAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                mrecycler_view.setVisibility(View.VISIBLE);

                Log.d("###Response",response.toString());
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {
                // Log error here since request failed
                Log.e("RESPONSE", t.toString());
            }
        });
    }


    @Override
    public Loader<List> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
//                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public List loadInBackground() {
                movies.clear();
                Uri favoriteMovieUri = PopularMovieEntry.BUILD_CONTENT;
                Cursor cursor = getContentResolver().query(
                        favoriteMovieUri,
                        FAV_MOVIE_COLUMNS,
                        null,
                        null,
                        null);

                if (cursor.moveToFirst()) {
                    do {
                        Movie movie = new Movie(
                                cursor.getInt(COL_MOVIE_ID),
                                cursor.getString(COL_POSTER_IMAGE),
                                cursor.getString(COL_OVERVIEW),
                                cursor.getString(COL_RELEASE_DATE),
                                cursor.getString(COL_TITLE),
                                cursor.getFloat(COL_RATING),
                                cursor.getString(COL_BACKDROP_IMAGE));
                        movies.add(movie);

                    } while (cursor.moveToNext());

                }

                cursor.close();
                return movies;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List data) {
        progressBar.setVisibility(View.GONE);
        mMovieDataAdapter = new MovieDataAdapter(MainActivity.this, movies);
        mrecycler_view.setAdapter(mMovieDataAdapter);
        mMovieDataAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (listState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_KEY,listState);
    }
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null)
            listState = state.getParcelable(LIST_KEY);
    }
}
