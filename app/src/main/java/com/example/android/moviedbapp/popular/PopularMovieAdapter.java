package com.example.android.moviedbapp.popular;

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
import butterknife.OnClick;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieViewHolder> {

    private List<PopularResult> popularResultList;
    private Context context;
    private PopularResult popularResult;


    public PopularMovieAdapter(Context context, List<PopularResult> resultsList) {
        this.context = context;
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
        popularResult = popularResultList.get(i);
        movieViewHolder.txtTitle.setText(popularResult.getTitle());
        movieViewHolder.txtDescription.setText(popularResult.getOverview());
        movieViewHolder.txtReleaseDate.setText(Util.getYearFromDate(popularResult.getReleaseDate()));
        movieViewHolder.txtVoteCount.setText(String.valueOf(popularResult.getVoteCount()));
        String imageUrl= Constants.POSTER_URL + popularResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return popularResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.popularItemParent) RelativeLayout parentLayout;
        @BindView(R.id.poster) ImageView imgPoster;
        @BindView(R.id.movieTitle) TextView txtTitle;
        @BindView(R.id.description) TextView txtDescription;
        @BindView(R.id.rDate) TextView txtReleaseDate;
        @BindView(R.id.voteCount) TextView txtVoteCount;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.popularItemParent) public void onMovieClick() {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.MOVIE_ID, popularResultList.get(getAdapterPosition()).getId());
            context.startActivity(intent);
        }
    }
}
