package com.example.android.moviedbapp.upcoming;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.upcoming_fragment, container, false);
        recyclerView = v.findViewById(R.id.upRecycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        return  v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieApiHandler movieApiHandler = retrofit.create(MovieApiHandler.class);
        Call<UpcomingMovieModel> call = movieApiHandler.getUpcomingMovies();
        call.enqueue(new Callback<UpcomingMovieModel>() {
            @Override
            public void onResponse(Call<UpcomingMovieModel> call, Response<UpcomingMovieModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<UpcomingResult> upcomingResults = response.body().getResults();
                adapter = new UpcomingMovieAdapter(upcomingResults);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<UpcomingMovieModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
