package com.example.android.moviedbapp.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.example.android.moviedbapp.details.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder>{

    private List<Result> searchResults;
    private Context context;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.searchPoster) ImageView imgPoster;
        @BindView(R.id.searchItemParent) RelativeLayout parentLayout;
        @BindView(R.id.searchMovieTitle) TextView txtTitle;
        @BindView(R.id.searchDescription) TextView txtDescription;
        @BindView(R.id.searchRDate) TextView txtReleaseDate;
        @BindView(R.id.searchVoteCount) TextView txtVoteCount;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            txtTitle = itemView.findViewById(R.id.searchMovieTitle);
            txtReleaseDate = itemView.findViewById(R.id.searchRDate);
            txtDescription = itemView.findViewById(R.id.searchDescription);
            txtVoteCount = itemView.findViewById(R.id.searchVoteCount);
            parentLayout = itemView.findViewById(R.id.searchItemParent);
            imgPoster = itemView.findViewById(R.id.searchPoster);
        }
    }

    public SearchMovieAdapter(Context context, List<Result> searchResults) {
        this.searchResults = searchResults;
        this.context = context;
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
        movieViewHolder.txtReleaseDate.setText(Util.getYearFromDate(result.getReleaseDate()));
        movieViewHolder.txtVoteCount.setText(String.valueOf(result.getVoteCount()));
        String imageUrl= Constants.POSTER_URL + result.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

        movieViewHolder.parentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.MOVIE_ID, result.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }
}
