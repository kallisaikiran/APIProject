package com.apiprojects.tmdbapp.ReyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apiprojects.tmdbapp.Fragments.Movies;
import com.apiprojects.tmdbapp.R;
import com.apiprojects.tmdbapp.RetrofitModel.MovieModel;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    List<MovieModel> movieModels;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_items, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movie_title.setText(movieModels.get(position).getTitle());
        holder.movie_releaseDate.setText(movieModels.get(position).getRelease_date());
        holder.ratingBar.setRating((float) (movieModels.get(position).getVote_average()/2.0));

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/original/"+movieModels.get(position).getPoster_path()).into(holder.movie_img);
    }

    @Override
    public int getItemCount() {

        if(movieModels!=null){
            return movieModels.size();
        }else{
            return 0;
        }

    }

    public void updateData(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView movie_img;
        TextView movie_title;
        TextView movie_releaseDate;
        RatingBar ratingBar;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_img = itemView.findViewById(R.id.movie_img);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_releaseDate = itemView.findViewById(R.id.movie_releaseDate);
            ratingBar = itemView.findViewById(R.id.rating_Bar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(itemView.getContext(), Movies.class);
                    i.putExtra("movie",movieModels.get(getAdapterPosition()));
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
