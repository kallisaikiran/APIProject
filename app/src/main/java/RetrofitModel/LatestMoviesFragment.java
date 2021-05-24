package RetrofitModel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    MovieAdapter movieAdapter;

    public static LatestMoviesFragment latestMoviesFragment() {
        LatestMoviesFragment fragment = new LatestMoviesFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter=new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);


        getlatestMovies();
        return view;
    }

    private  void getlatestMovies() {
        MovieApi movieApi = ServiceGenerator.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.getNowPlaying(Credentials.Api_Key, "en-US", 1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
                        Log.v("Tag", movie.getTitle());
                    }
                    movieAdapter.updateData(movies);

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }
}
