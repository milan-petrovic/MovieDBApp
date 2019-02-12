package com.example.android.moviedbapp.popular;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.moviedbapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {

    private List<PopularResult> popularResultList;
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPoster;
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtReleaseDate;
        public TextView txtVoteCount;
        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.movieTitle);
            txtReleaseDate = (TextView)itemView.findViewById(R.id.rDate);
            txtDescription = (TextView)itemView.findViewById(R.id.description);
            txtVoteCount = (TextView)itemView.findViewById(R.id.voteCount);

            imgPoster = (ImageView)itemView.findViewById(R.id.poster);
        }
    }

    public PopularMovieAdapter(List<PopularResult> resultsList) {
        this.popularResultList = resultsList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.popular_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return  mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        PopularResult popularResult = popularResultList.get(i);
        movieViewHolder.txtTitle.setText(popularResult.getTitle());
        movieViewHolder.txtDescription.setText(popularResult.getOverview());
        movieViewHolder.txtReleaseDate.setText(popularResult.getReleaseDate());
        movieViewHolder.txtVoteCount.setText(String.valueOf(popularResult.getVoteCount()));
        String imageUrl="https://image.tmdb.org/t/p/w500/" + popularResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return popularResultList.size();
    }
}
