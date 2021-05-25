package com.apiprojects.tmdbapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import com.apiprojects.tmdbapp.Credentials;
import com.apiprojects.tmdbapp.R;
import com.apiprojects.tmdbapp.RetrofitModel.MovieApi;
import com.apiprojects.tmdbapp.RetrofitModel.MovieModel;
import com.apiprojects.tmdbapp.RetrofitModel.MovieSearchResponse;
import com.apiprojects.tmdbapp.ReyclerView.MovieAdapter;
import com.apiprojects.tmdbapp.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    MovieAdapter movieAdapter;
    public static HomeFragment homeFragment() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    Button test;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
      recyclerView=view.findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter=new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);


   getPopularMovies();
        return view;
    }

    private  void getPopularMovies(){
        MovieApi movieApi= ServiceGenerator.getMovieApi();
        Call<MovieSearchResponse> responseCall=movieApi.getPopularMovies(Credentials.Api_Key,"en-US",1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code()==200){

                    List<MovieModel> movies=new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie:movies){
                        Log.v("Tag",movie.getTitle());
                    }
                    movieAdapter.updateData(movies);

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
    private void getRetrofitResponseThroughId(){
MovieApi movieApi=ServiceGenerator.getMovieApi();
Call<MovieModel> responseCall=movieApi.getMovie(550,Credentials.Api_Key);
responseCall.enqueue(new Callback<MovieModel>() {
    @Override
    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
        if(response.code()==200){
            MovieModel movie=response.body();
            Log.v("KruthikTag",movie.getTitle());
        }
    }

    @Override
    public void onFailure(Call<MovieModel> call, Throwable t) {

    }
});
    }
}
