package com.example.android.moviedbapp.top_rated;

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
import com.example.android.moviedbapp.NetworkSourceData;
import com.example.android.moviedbapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopRatedFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<TopRatedModel> call = movieApiHandler.getTopRated();
        call.enqueue(new Callback<TopRatedModel>() {
            @Override
            public void onResponse(Call<TopRatedModel> call, Response<TopRatedModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<TopRatedResult> topRatedTopRatedResults = response.body().getTopRatedResults();
                adapter = new TopRatedMovieAdapter(getActivity(), topRatedTopRatedResults);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<TopRatedModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.top_rated_fragment, container, false);
        recyclerView = v.findViewById(R.id.trRecycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        return  v;
    }
}
