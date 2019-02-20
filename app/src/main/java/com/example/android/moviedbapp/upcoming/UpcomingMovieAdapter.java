package com.example.android.moviedbapp.upcoming;

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

public class UpcomingMovieAdapter extends RecyclerView.Adapter<UpcomingMovieAdapter.MovieViewHolder> {

    private Context context;
    private List<UpcomingResult> upcomingResults;

    public class  MovieViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.upcomingParent) RelativeLayout upcomingParent;
        @BindView(R.id.upMovieTitle) TextView txtTitle;
        @BindView(R.id.upDescription) TextView txtDescription;
        @BindView(R.id.upVoteAverage) TextView txtCountAverage;
        @BindView(R.id.upPoster) ImageView imgPoster;
        @BindView(R.id.upRDate) TextView txtReleaseDate;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public UpcomingMovieAdapter(Context context, List<UpcomingResult> upcomingResultList) {
        this.context = context;
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
        movieViewHolder.txtReleaseDate.setText(Util.getYearMonthDay(upcomingResult.getReleaseDate()));
        movieViewHolder.txtCountAverage.setText(String.valueOf(upcomingResult.getVoteAverage()));
        movieViewHolder.txtDescription.setText(upcomingResult.getOverview());
        String imageUrl= Constants.POSTER_URL + upcomingResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

        movieViewHolder.upcomingParent.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.MOVIE_ID, upcomingResult.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return upcomingResults.size();
    }
}
