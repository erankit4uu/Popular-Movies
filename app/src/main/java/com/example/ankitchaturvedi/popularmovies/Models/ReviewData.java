package com.example.ankitchaturvedi.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ankit.Chaturvedi on 28-Apr-17.
 */

public class ReviewData {
    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int total_results;

    ArrayList<Reviews> results;

    public ArrayList<Reviews> getResults(){
        return results;
    }
}
