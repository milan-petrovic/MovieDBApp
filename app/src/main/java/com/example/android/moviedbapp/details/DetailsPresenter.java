package com.example.android.moviedbapp.details;

import android.text.TextUtils;

import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;

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
        view.showProgress();

        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<MovieDetailsModel> call = movieApiHandler.getMovieDetails(String.valueOf(id));
        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                MovieDetailsModel movieDetails = response.body();
                view.showMovieMainDetails(movieDetails);
                String genre = genresFromList(movieDetails.getGenres());
                view.showMovieGengres(genre);
                view.showMovieFacts(movieDetails);
                view.showMovieRates(movieDetails);
                view.showMoviePoster(Constants.POSTER_URL + movieDetails.getPosterPath());
                view.showMovieBackdrop(Constants.BACKDROP_URL + movieDetails.getBackdropPath());
                view.showMovieDescription(movieDetails.getOverview());
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
                view.hideProgress();
            }
        });

        Call<SimilarMovies> callSimilar = movieApiHandler.getSimilarMovies(String.valueOf(id));
        callSimilar.enqueue(new Callback<SimilarMovies>() {
            @Override
            public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                SimilarMovies similarMovies = response.body();
                view.showSimilarMoviesMainDetails(similarMovies);
            }
            @Override
            public void onFailure(Call<SimilarMovies> call, Throwable t) {
                t.printStackTrace();
                view.displayError();
                view.hideProgress();
            }
        });

        Call<MovieCast> callCast = movieApiHandler.getMovieCast(String.valueOf(id));
        callCast.enqueue(new Callback<MovieCast>() {
            @Override
            public void onResponse(Call<MovieCast> call, Response<MovieCast> response) {
                if (!response.isSuccessful()) {
                    view.displayError();
                    return;
                }
                MovieCast movieCast = response.body();
                view.showMovieCast(movieCast);
            }

            @Override
            public void onFailure(Call<MovieCast> call, Throwable t) {

            }
        });

    }

    private String genresFromList(List<Genre> genres) {
        String genre = "";
        for (Genre g : genres) {
            if (TextUtils.isEmpty(genre)) {
                genre = g.getName();
            } else {
                genre = genre + ", " + g.getName();
            }
        }
        return genre;
    }

    public interface View {
        void showMoviePoster(String posterUrl);
        void showMovieBackdrop(String backdropUrl);
        void showMovieRates(MovieDetailsModel movieDetailsModel);
        void showMovieMainDetails(MovieDetailsModel movieDetailsModel);
        void showMovieDescription(String overview);
        void showMovieGengres(String genre);
        void showMovieFacts(MovieDetailsModel movieDetailsModel);
        void showSimilarMoviesMainDetails(SimilarMovies similarMovies);
        void showProgress();
        void hideProgress();
        void displayError();
        void showMovieCast(MovieCast movieCast);
    }
}
