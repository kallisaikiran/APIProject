package com.apiprojects.tmdbapp.RetrofitModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie_Response {

    @SerializedName("results")
    @Expose
    private MovieModel movieModel ;

    public MovieModel getMovieModel() {
        return movieModel;
    }

    @Override
    public String toString() {
        return "Movie_Response{" +
                "movieModel=" + movieModel +
                '}';
    }
}
