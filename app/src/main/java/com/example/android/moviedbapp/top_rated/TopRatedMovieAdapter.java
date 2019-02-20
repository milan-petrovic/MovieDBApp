package com.example.android.moviedbapp.top_rated;

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

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.MovieViewHolder> {

    private Context context;
    private List<TopRatedResult> topRatedTopRatedResults;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topRatedParent) RelativeLayout topRatedParent;
        @BindView(R.id.trMovieTitle) TextView txtTitle;
        @BindView(R.id.trDescription) TextView txtDescription;
        @BindView(R.id.trVoteAverage) TextView txtCountAverage;
        @BindView(R.id.trPoster) ImageView imgPoster;
        @BindView(R.id.trRDate) TextView txtReleaseDate;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public TopRatedMovieAdapter(Context context, List<TopRatedResult> topRatedTopRatedResults) {
        this.topRatedTopRatedResults = topRatedTopRatedResults;
        this.context = context;
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
        TopRatedResult topRatedResult = topRatedTopRatedResults.get(i);
        movieViewHolder.txtTitle.setText(topRatedResult.getTitle());
        movieViewHolder.txtReleaseDate.setText(Util.getYearFromDate(topRatedResult.getReleaseDate()));
        movieViewHolder.txtCountAverage.setText(String.valueOf(topRatedResult.getVoteAverage()));
        movieViewHolder.txtDescription.setText(topRatedResult.getOverview());
        String imageUrl= Constants.POSTER_URL + topRatedResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

        movieViewHolder.topRatedParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(Constants.MOVIE_ID, topRatedResult.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topRatedTopRatedResults.size();
    }
}
