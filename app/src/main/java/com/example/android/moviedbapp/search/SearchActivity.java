package com.example.android.moviedbapp.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;
import com.example.android.moviedbapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = findViewById(R.id.searchRecycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doQuery(query);
        }
    }

    public void doQuery(String query) {
        MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
        Call<SearchMovieModel> call = movieApiHandler.getSearchMovies(query);
        call.enqueue(new Callback<SearchMovieModel>() {
            @Override
            public void onResponse(Call<SearchMovieModel> call, Response<SearchMovieModel> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Result> searchResults = response.body().getResults();
                adapter = new SearchMovieAdapter(SearchActivity.this, searchResults);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<SearchMovieModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
