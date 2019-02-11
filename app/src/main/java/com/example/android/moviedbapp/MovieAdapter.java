package com.example.android.moviedbapp;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> resultList;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imgView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtView);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }

    public MovieAdapter(List<Result> resultsList) {
        this.resultList = resultsList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_popular, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return  mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Result result = resultList.get(i);
        movieViewHolder.textView.setText("Title: " + result.getTitle());
        String imageUrl="https://image.tmdb.org/t/p/w500/" + result.getPosterPath();
        Picasso.get().load(imageUrl).into(movieViewHolder.imgView);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}
