package com.example.android.moviedbapp;

import com.example.android.moviedbapp.details.MovieDetailsModel;
import com.example.android.moviedbapp.popular.PopularModel;
import com.example.android.moviedbapp.top_rated.TopRatedModel;
import com.example.android.moviedbapp.upcoming.UpcomingMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiHandler {
    @GET("3/movie/popular?api_key=ecfe957f81c6a84027d326ccd2cd19fd&language=en-US&page=1")
    Call<PopularModel> getPopularMovies();
    @GET("3/movie/top_rated?api_key=ecfe957f81c6a84027d326ccd2cd19fd&language=en-US&page=1")
    Call<TopRatedModel> getTopRated();
    @GET("3/movie/upcoming?api_key=ecfe957f81c6a84027d326ccd2cd19fd&language=en-US&page=1")
    Call<UpcomingMovieModel> getUpcomingMovies();
    @GET("3/movie/{movie_id}?api_key=ecfe957f81c6a84027d326ccd2cd19fd&language=en-US")
    Call<MovieDetailsModel> getMovieDetails(@Path("movie_id") String movieId);


}
