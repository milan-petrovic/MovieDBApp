package com.example.android.moviedbapp.search;

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

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>{

    //radim bez contexta

    private List<Result> searchResults;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPoster;
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtReleaseDate;
        public TextView txtVoteCount;
        public MovieViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.searchMovieTitle);
            txtReleaseDate = (TextView)itemView.findViewById(R.id.searchRDate);
            txtDescription = (TextView)itemView.findViewById(R.id.searchDescription);
            txtVoteCount = (TextView)itemView.findViewById(R.id.searchVoteCount);
            //parentLayout = itemView.findViewById(R.id.popularItemParent);

            imgPoster = (ImageView)itemView.findViewById(R.id.searchPoster);
        }
    }

    public SearchMovieAdapter(List<Result> searchResults) {
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Result result = searchResults.get(i);
        movieViewHolder.txtTitle.setText(result.getTitle());
        movieViewHolder.txtDescription.setText(result.getOverview());
        String date = Util.getYearFromDate(result.getReleaseDate());
        movieViewHolder.txtReleaseDate.setText(date);
        movieViewHolder.txtVoteCount.setText(String.valueOf(result.getVoteCount()));
        String imageUrl="https://image.tmdb.org/t/p/w500/" + result.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }
}
