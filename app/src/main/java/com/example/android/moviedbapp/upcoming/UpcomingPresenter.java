package com.example.android.moviedbapp.upcoming;


import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingPresenter {
    private View view;

    public UpcomingPresenter() {}


    public void setView(View view) {
        this.view = view;
    }

    public void getUpcomingMovies() {

        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<UpcomingMovieModel> call = movieApiHandler.getUpcomingMovies();
        call.enqueue(new Callback<UpcomingMovieModel>() {
            @Override
            public void onResponse(Call<UpcomingMovieModel> call, Response<UpcomingMovieModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                view.displayUpcomingMovies(response.body().getResults());
            }
            @Override
            public void onFailure(Call<UpcomingMovieModel> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
            }
        });

    }

    public interface View {
        void displayUpcomingMovies(List<UpcomingResult> upcomingResults);
        void displayError();
    }
}
