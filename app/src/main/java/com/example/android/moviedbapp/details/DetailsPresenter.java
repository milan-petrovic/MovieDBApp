package com.example.android.moviedbapp.details;

import android.text.TextUtils;
import android.view.View;

import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;
import com.example.android.moviedbapp.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter {

    private View view;

    public DetailsPresenter() {
    }

    public void setView(View view) {
        this.view = view;
    }

    public void getMovieDetails(int id) {
        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<MovieDetailsModel> call = movieApiHandler.getMovieDetails(String.valueOf(id));
        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                view.showMovieDetails(response.body(), response.body().getGenres());

            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
            }
        });

    }

    public interface View {
        void showMovieDetails(MovieDetailsModel movieDetailsModel, List<Genre> genres);
        void displayError();
    }
}
