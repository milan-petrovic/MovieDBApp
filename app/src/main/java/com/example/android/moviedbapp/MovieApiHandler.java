package com.example.android.moviedbapp;

import com.example.android.moviedbapp.popular.PopularModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiHandler {
    @GET("3/movie/popular?api_key=ecfe957f81c6a84027d326ccd2cd19fd&language=en-US&page=1")
    Call<PopularModel> getTopMovies();
}
