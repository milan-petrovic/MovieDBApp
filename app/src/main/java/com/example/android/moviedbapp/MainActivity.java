package com.example.android.moviedbapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.moviedbapp.popular.PopularMovieAdapter;
import com.example.android.moviedbapp.popular.PopularModel;
import com.example.android.moviedbapp.popular.PopularResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        MovieApiHandler movieApiHandler = retrofit.create(MovieApiHandler.class);

        Call<PopularModel> call = movieApiHandler.getTopMovies();
        call.enqueue(new Callback<PopularModel>() {
            @Override
            public void onResponse(Call<PopularModel> call, Response<PopularModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<PopularResult> moviePopularResults = response.body().getPopularResults();
                for(PopularResult popularResult : moviePopularResults) {
                    Log.d("TAG", popularResult.getTitle());
                }
                adapter = new PopularMovieAdapter(moviePopularResults);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<PopularModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
