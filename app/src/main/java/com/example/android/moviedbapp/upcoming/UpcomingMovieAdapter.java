package com.example.android.moviedbapp.upcoming;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.MovieViewHolder> {

    private List<UpcomingResult> upcomingResults;

    public class  MovieViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtCountAverage;
        public ImageView imgPoster;
        public TextView txtReleaseDate;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.upMovieTitle);
            txtDescription = (TextView)itemView.findViewById(R.id.upDescription);
            txtCountAverage = (TextView)itemView.findViewById(R.id.upVoteAverage);
            imgPoster = (ImageView)itemView.findViewById(R.id.upPoster);
            txtReleaseDate = (TextView)itemView.findViewById(R.id.upRDate);
        }
    }

    public UpcomingMovieAdapter(List<UpcomingResult> upcomingResultList) {
        this.upcomingResults = upcomingResultList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.upcoming_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        UpcomingResult upcomingResult = upcomingResults.get(i);
        movieViewHolder.txtTitle.setText(upcomingResult.getTitle());
        movieViewHolder.txtReleaseDate.setText(upcomingResult.getReleaseDate());
        movieViewHolder.txtCountAverage.setText(String.valueOf(upcomingResult.getVoteAverage()));
        movieViewHolder.txtDescription.setText(upcomingResult.getOverview());
        String imageUrl="https://image.tmdb.org/t/p/w500/" + upcomingResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return upcomingResults.size();
    }
}
