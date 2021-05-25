package com.apiprojects.tmdbapp;

import com.apiprojects.tmdbapp.RetrofitModel.MovieApi;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Credentials.Base_Url).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit=builder.build();

    private  static MovieApi movieApi=retrofit.create(MovieApi.class);
    public static MovieApi getMovieApi(){
        return movieApi;
    }
}
