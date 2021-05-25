package com.apiprojects.tmdbapp.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apiprojects.tmdbapp.R;
import com.apiprojects.tmdbapp.RetrofitModel.MovieModel;
import com.bumptech.glide.Glide;

public class Movies extends AppCompatActivity {
MovieModel movieModel;


    ImageView movie_img;
    TextView movie_title;
    TextView movie_releaseDate;
    RatingBar ratingBar;
    TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movie_img =findViewById(R.id.movie_img);
        movie_title = findViewById(R.id.movie_title);
        movie_releaseDate = findViewById(R.id.movie_releaseDate);
        ratingBar = findViewById(R.id.rating_Bar);
        description=findViewById(R.id.description);
        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("movie")){
             movieModel = getIntent().getParcelableExtra("movie");
            Toast.makeText(this,movieModel.getTitle(),Toast.LENGTH_LONG).show();
            Glide.with(this).load("https://image.tmdb.org/t/p/original/"+movieModel.getPoster_path()).into(movie_img);
            movie_title.setText(movieModel.getTitle());
            movie_releaseDate.setText(movieModel.getRelease_date());
            ratingBar.setRating((float) (movieModel.getVote_average()/2.0));
            description.setText(movieModel.getMovie_overview());
        }
    }
}