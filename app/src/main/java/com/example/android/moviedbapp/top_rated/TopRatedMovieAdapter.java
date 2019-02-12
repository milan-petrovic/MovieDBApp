package com.example.android.moviedbapp.top_rated;

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

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.MovieViewHolder> {



    private List<Result> topRatedResults;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtCountAverage;
        public ImageView imgPoster;
        public TextView txtReleaseDate;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.trMovieTitle);
            txtDescription = (TextView)itemView.findViewById(R.id.trDescription);
            txtCountAverage = (TextView)itemView.findViewById(R.id.trVoteAverage);
            txtReleaseDate = (TextView)itemView.findViewById(R.id.trRDate);
            imgPoster = (ImageView) itemView.findViewById(R.id.trPoster);
        }
    }

    public TopRatedMovieAdapter(List<Result> topRatedResults) {
        this.topRatedResults = topRatedResults;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_rated_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Result result = topRatedResults.get(i);
        movieViewHolder.txtTitle.setText(result.getTitle());
        movieViewHolder.txtReleaseDate.setText(result.getReleaseDate());
        movieViewHolder.txtCountAverage.setText(String.valueOf(result.getVoteAverage()));
        movieViewHolder.txtDescription.setText(result.getOverview());
        String imageUrl="https://image.tmdb.org/t/p/w500/" + result.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return topRatedResults.size();
    }
}
