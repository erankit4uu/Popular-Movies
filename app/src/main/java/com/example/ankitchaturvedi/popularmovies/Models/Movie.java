package com.example.ankitchaturvedi.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankit.Chaturvedi on 26-Apr-17.
 */

public class Movie implements Parcelable {

    @SerializedName("poster_path")
    public String poster_path;

    @SerializedName("adult")
    public Boolean adult;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String release_date;

    @SerializedName("original_title")
    public String original_title;

    @SerializedName("title")
    public String title;

    @SerializedName("popularity")
    public Double popularity;

    @SerializedName("vote_average")
    public Float vote_average;

    @SerializedName("id")
    public int id;

    @SerializedName("backdrop_path")
    public String backdrop_path;

    @SerializedName("original_language")
    public String original_language;

    @SerializedName("vote_count")
    public int vote_count;

    public boolean video;



    public Movie(int id,String poster_path,String overview,String release_date,String title,
                 Float vote_average,String backdrop_path){
        this.overview = overview;
        this.poster_path = poster_path;
        this.adult = adult;
        this.release_date = release_date;
        this.original_title= original_title;
        this.title = title;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
        this.id = id;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public Boolean getAdult(){
        return adult;
    }

    public  String getOverview(){
        return overview;
    }

    public  String getOriginal_title(){
        return original_title;
    }

    public String getTitle(){
        return title;
    }

    public String getRelease_date(){
        return release_date;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }

    public String getOriginal_language(){
        return original_language;
    }

    public int getId(){
        return id;
    }

    public int getVote_count(){
        return vote_count;
    }

    public Float getVote_average(){
        return vote_average;
    }

    public Double getPopularity(){
        return popularity;
    }


    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        title = in.readString();
        id = in.readInt();
        backdrop_path = in.readString();
        original_language = in.readString();
        vote_count = in.readInt();
        video = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public String toString() {
        return "Movie {poster_path: " + poster_path + ", adult: " + adult
                + ", overview: " + overview + ", release_date: " + release_date + ", id: "
                + id + ", original_title: " + original_title + ", original_language: "
                + original_language + ", title: " + title + ", backdrop_path: " + backdrop_path
                + ", popularity: " + popularity + ", vote_count: "
                + vote_count + ", video: " + video
                + ", vote_average: " + vote_average + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(original_title);
        parcel.writeString(title);
        parcel.writeInt(id);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_language);
        parcel.writeInt(vote_count);
        parcel.writeByte((byte) (video ? 1 : 0));
    }
}
