package com.example.android.moviedbapp.popular;

import com.example.android.moviedbapp.MovieApiHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PopularPresenter {

    private Retrofit retrofit;
    private View view;

    public PopularPresenter(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void getPopularMovies() {
        MovieApiHandler movieApiHandler = retrofit.create(MovieApiHandler.class);
        Call<PopularModel> call = movieApiHandler.getPopularMovies();
        call.enqueue(new Callback<PopularModel>() {
            @Override
            public void onResponse(Call<PopularModel> call, Response<PopularModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                view.displayPopularMovies(response.body().getPopularResults());
            }

            @Override
            public void onFailure(Call<PopularModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface View {

        void displayPopularMovies(List<PopularResult> popularResults);

        void displayError();
    }
}
