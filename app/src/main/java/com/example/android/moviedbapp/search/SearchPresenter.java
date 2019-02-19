package com.example.android.moviedbapp.search;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private View view;

    public SearchPresenter() {
    }

    public void setView(View view) {
        this.view = view;
    }

    public void getSearchMovies(String query) {
        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<SearchMovieModel> call = movieApiHandler.getSearchMovies(query);
        call.enqueue(new Callback<SearchMovieModel>() {
            @Override
            public void onResponse(Call<SearchMovieModel> call, Response<SearchMovieModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                view.displaySearchMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<SearchMovieModel> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
            }
        });
    }

    public interface View {
        void displaySearchMovies(List<Result> searchResults);
        void displayError();
    }
}
