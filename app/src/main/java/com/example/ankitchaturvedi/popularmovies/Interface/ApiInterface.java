package com.example.ankitchaturvedi.popularmovies.Interface;

import com.example.ankitchaturvedi.popularmovies.Models.MovieData;
import com.example.ankitchaturvedi.popularmovies.Models.ReviewData;
import com.example.ankitchaturvedi.popularmovies.Models.TrailerData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ankit.Chaturvedi on 27-Apr-17.
 */

public interface ApiInterface {


    @GET("movie/popular")
    Call<MovieData> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieData> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieData> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/{path}")
    Call<TrailerData> getMovieTrailers(@Path("id") int id, @Path("path") String path, @Query("api_key") String apiKey);

    @GET("movie/{id}/{path}")
    Call<ReviewData> getMovieReviews(@Path("id") int id, @Path("path") String path, @Query("api_key") String apiKey);
}
