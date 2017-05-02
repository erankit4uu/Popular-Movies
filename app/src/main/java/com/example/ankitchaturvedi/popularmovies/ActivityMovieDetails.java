package com.example.ankitchaturvedi.popularmovies;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankitchaturvedi.popularmovies.Adapter.MovieDataAdapter;
import com.example.ankitchaturvedi.popularmovies.Adapter.MovieTrailerAdapter;
import com.example.ankitchaturvedi.popularmovies.Adapter.ReviewsAdapter;
import com.example.ankitchaturvedi.popularmovies.Database.PopularMovieContract;
import com.example.ankitchaturvedi.popularmovies.Interface.ApiInterface;
import com.example.ankitchaturvedi.popularmovies.Models.Movie;
import com.example.ankitchaturvedi.popularmovies.Models.MovieData;
import com.example.ankitchaturvedi.popularmovies.Models.ReviewData;
import com.example.ankitchaturvedi.popularmovies.Models.Reviews;
import com.example.ankitchaturvedi.popularmovies.Models.TrailerData;
import com.example.ankitchaturvedi.popularmovies.Models.Trailers;
import com.example.ankitchaturvedi.popularmovies.utils.ApiClient;
import com.example.ankitchaturvedi.popularmovies.utils.Constant;
import com.example.ankitchaturvedi.popularmovies.utils.helper;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ankit.Chaturvedi on 04-Apr-17.
 */

public class ActivityMovieDetails extends AppCompatActivity {
    TextView mtv_movie_title, mtv_release_date, mtv_overview, mtv_censor_rating, mtv_rating;
    ImageView miv_collapsing;
    RatingBar rating_bar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressBar progressBar;
    FloatingActionButton mfab_trailers, mfab_favrourite;
    LinearLayout mll_movie_details,mll_trailer;
    RecyclerView mrv_trailers;
    private static String mposter_path, mbackdrop_path,title, overview, release, success, failure,dbTitle;
    private static int id;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        mtv_movie_title = (TextView) findViewById(R.id.tv_movie_title);
        miv_collapsing = (ImageView) findViewById(R.id.iv_collapsing);
        mtv_overview = (TextView) findViewById(R.id.tv_overview);
        mtv_release_date = (TextView) findViewById(R.id.tv_release_date);
        mtv_censor_rating = (TextView) findViewById(R.id.tv_censor_rating);
        mtv_rating = (TextView) findViewById(R.id.tv_rating);
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mfab_trailers = (FloatingActionButton) findViewById(R.id.fab_trailers);
        mfab_favrourite = (FloatingActionButton) findViewById(R.id.fab_favourite);
        mll_movie_details = (LinearLayout) findViewById(R.id.ll_movie_details);
        mll_trailer = (LinearLayout) findViewById(R.id.ll_trailer);
        mrv_trailers = (RecyclerView) findViewById(R.id.rv_trailers);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityMovieDetails.this);
        mrv_trailers.setLayoutManager(linearLayoutManager);

        mfab_trailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<TrailerData> call = apiService.getMovieTrailers(id,"videos",Constant.api_key);
                call.enqueue(new Callback<TrailerData>() {
                    @Override
                    public void onResponse(Call<TrailerData> call, Response<TrailerData> response) {
                        List<Trailers> trailers = response.body().getResults();
                        mrv_trailers.setAdapter(new MovieTrailerAdapter(ActivityMovieDetails.this, trailers));
                        progressBar.setVisibility(View.GONE);
                        mrv_trailers.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<TrailerData> call, Throwable t) {
                        // Log error here since request failed
                        Log.e("RESPONSE", t.toString());
                    }
                });
            }
        });

        mfab_favrourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFavouriteMovieAsync favouriteMovieAsync = new SetFavouriteMovieAsync(ActivityMovieDetails.this);
                favouriteMovieAsync.execute();
            }
        });

        Intent intent = getIntent();
        mtv_movie_title.setText(String.format("Title: %s", intent.getStringExtra("title")));
        title = intent.getStringExtra("title");
        mbackdrop_path = intent.getStringExtra("backdrop_path");
        mposter_path = intent.getStringExtra("poster_path");
        mtv_overview.setText(String.format("Overview: %s", intent.getStringExtra("overview")));
        overview = intent.getStringExtra("overview");
        mtv_release_date.setText(String.format("Release Date: %s", intent.getStringExtra("release_date")));
        release = intent.getStringExtra("release_date");
        rating_bar.setRating(intent.getFloatExtra("vote_average", 0));
        id = (intent.getIntExtra("id", 0));
        rating_bar.setClickable(false);
        mtv_rating.setText(String.format("%s/10", rating_bar.getRating()));
        if (intent.getBooleanExtra("adult", false)) {
            mtv_censor_rating.setVisibility(View.VISIBLE);
        } else mtv_censor_rating.setVisibility(View.GONE);
        setTitle(intent.getStringExtra("title"));
        getReviews();


        Cursor cursor = ActivityMovieDetails.this.getContentResolver().query(
                PopularMovieContract.PopularMovieEntry.BUILD_CONTENT,
                new String[]{PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID},
                PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)},
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
            mfab_favrourite.setImageResource(R.drawable.ic_favorite_white_24dp);
            }
        }
        Picasso.with(ActivityMovieDetails.this)
                .load(helper.base_url + helper.poster_sizesw184 + mbackdrop_path)
                .into(miv_collapsing);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

    private void getReviews() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ReviewData> call = apiService.getMovieReviews(id,"reviews",Constant.api_key);
        call.enqueue(new Callback<ReviewData>() {
            @Override
            public void onResponse(Call<ReviewData> call, Response<ReviewData> response) {
                List<Reviews> reviews = response.body().getResults();
                mrv_trailers.setAdapter(new ReviewsAdapter(ActivityMovieDetails.this, reviews));
                progressBar.setVisibility(View.GONE);
                mrv_trailers.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ReviewData> call, Throwable t) {
                // Log error here since request failed
                Log.e("RESPONSE", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReviews();
    }



    private class SetFavouriteMovieAsync extends AsyncTask<Void, Void, Void> {

        private Context mContext;

        public SetFavouriteMovieAsync(Context context) {
            mContext = context;

        }

        @Override
        protected Void doInBackground(Void... params) {
            SaveAndDeleteMovieRow();
            return null;
        }

        private void SaveAndDeleteMovieRow() {

            Cursor cursor = ActivityMovieDetails.this.getContentResolver().query(
                    PopularMovieContract.PopularMovieEntry.BUILD_CONTENT,
                    new String[]{PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID},
                    PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null);


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int rowDeleted = ActivityMovieDetails.this.getContentResolver().delete(PopularMovieContract.PopularMovieEntry.BUILD_CONTENT,
                            PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID + " = ?",
                            new String[]{String.valueOf(id)});
                    if (rowDeleted > 0) {
                        success = "Deleted from Favourite";
                    } else {
                        failure = "Failed";
                    }

                } else {


                    ContentValues values = new ContentValues();
                    values.put(PopularMovieContract.PopularMovieEntry.COL_MOVIE_ID, id);
                    values.put(PopularMovieContract.PopularMovieEntry.COL_TITLE, title);
                    values.put(PopularMovieContract.PopularMovieEntry.COL_POSTER_IMAGE, mposter_path);
                    values.put(PopularMovieContract.PopularMovieEntry.COL_OVERVIEW, overview);
                    values.put(PopularMovieContract.PopularMovieEntry.COL_RATINGS, mtv_rating.getText().toString());
                    values.put(PopularMovieContract.PopularMovieEntry.COL_RELEASE_DATE, release);
                    values.put(PopularMovieContract.PopularMovieEntry.COL_BACKDROP_IMAGE, mbackdrop_path);


                    Uri uri = getContentResolver().insert(
                            PopularMovieContract.PopularMovieEntry.BUILD_CONTENT,
                            values);

                    long movieRowId = ContentUris.parseId(uri);

                    if (movieRowId > 0) {
                        success = "Added As Favourite";
                    } else {
                        failure = "Failed";
                        success = "";
                    }

                    cursor.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (success) {
                case "Added As Favourite":
                    Toast.makeText(mContext, "Added To Favourite", Toast.LENGTH_LONG).show();
                    mfab_favrourite.setImageResource(R.drawable.ic_favorite_white_24dp);
                    break;
                case "Deleted from Favourite":
                    Toast.makeText(mContext, "Deleted from Favourite", Toast.LENGTH_LONG).show();
                    mfab_favrourite.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    break;
                default:
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
