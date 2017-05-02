package com.example.ankitchaturvedi.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ankit.Chaturvedi on 28-Apr-17.
 */

public class TrailerData {

    @SerializedName("id")
    public int id;

    private ArrayList<Trailers> results;
    public ArrayList<Trailers> getResults() {
        return results;
    }

    public int getId(){
        return id;
    }
}
