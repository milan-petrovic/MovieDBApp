package com.example.android.moviedbapp.top_rated;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedPresenter {

    private View view;


    public TopRatedPresenter() {

    }


    public void getTopRatedMovies() {
        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<TopRatedModel> call = movieApiHandler.getTopRated();
        call.enqueue(new Callback<TopRatedModel>() {
            @Override
            public void onResponse(Call<TopRatedModel> call, Response<TopRatedModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                view.displayTopRatedMovies(response.body().getTopRatedResults());
            }

            @Override
            public void onFailure(Call<TopRatedModel> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
            }
        });

    }

    public void setView(View view) {
        this.view = view;
    }

    public interface View {
        void displayTopRatedMovies(List <TopRatedResult> topRatedResults);
        void displayError();
    }
}
