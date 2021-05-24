package RetrofitModel;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {


    //https://image.tmdb.org/t/p/original/3/movie/{now_playing}
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query
            , @Query("page") int page);

    @GET("3/movie/{movie_id}")
    Call<MovieModel> getMovie(@Path("movie_id") int movie_id,
                              @Query("api_key") String api_key);

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(@Query("api_key") String key,
                                               @Query("language") String language
            , @Query("page") int page);

    @GET("/3/movie/now_playing")
    Call<MovieSearchResponse> getNowPlaying(@Query("api_key") String key,
                                            @Query("language") String language
            , @Query("page") int page);

    @GET("/3/movie/upcoming")
    Call<MovieSearchResponse> getUpcomingMovies(@Query("api_key") String key,
                                                @Query("language") String language
            , @Query("page") int page);
}
