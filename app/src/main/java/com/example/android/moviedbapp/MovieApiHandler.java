package com.example.android.moviedbapp;

import com.example.android.moviedbapp.details.MovieDetailsModel;
import com.example.android.moviedbapp.popular.PopularModel;
import com.example.android.moviedbapp.search.SearchMovieModel;
import com.example.android.moviedbapp.top_rated.TopRatedModel;
import com.example.android.moviedbapp.upcoming.UpcomingMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiHandler {
    @GET("3/movie/popular?language=en-US&page=1")
    Call<PopularModel> getPopularMovies();
    @GET("3/movie/top_rated?language=en-US&page=1")
    Call<TopRatedModel> getTopRated();
    @GET("3/movie/upcoming?language=en-US&page=1")
    Call<UpcomingMovieModel> getUpcomingMovies();
    @GET("3/movie/{movie_id}?language=en-US")
    Call<MovieDetailsModel> getMovieDetails(@Path("movie_id") String movieId);
    @GET("3/search/movie?language=en-US&query=&page=1&include_adult=false")
    Call<SearchMovieModel> getSearchMovies(@Query("query") String query);
}
