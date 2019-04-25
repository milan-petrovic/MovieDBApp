package com.example.android.moviedbapp.details;

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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SimilarMovieAdapter extends RecyclerView.Adapter<SimilarMovieAdapter.MovieViewHolder> {
    private List<SimilarMoviesResult> similarMoviesResultList;
    private Context context;
    private SimilarMoviesResult similarMoviesResult;

    public SimilarMovieAdapter(Context context, List<SimilarMoviesResult> resultList) {
        this.context = context;
        this.similarMoviesResultList = resultList;
    }

    @NonNull
    @Override
    public SimilarMovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.similar_item, viewGroup, false);
        MovieViewHolder mvh = new MovieViewHolder(v);
        return  mvh;    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMovieAdapter.MovieViewHolder movieViewHolder, int i) {
        similarMoviesResult = similarMoviesResultList.get(i);
        movieViewHolder.txtSimilarMovieTitle.setText(similarMoviesResult.getTitle());
        movieViewHolder.txtSimilarYear.setText(Util.getYearFromDate(similarMoviesResult.getReleaseDate()));
        String imageUrl = Constants.POSTER_URL + similarMoviesResult.getPosterPath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgSimilarPoster);
    }

    @Override
    public int getItemCount() {
        return similarMoviesResultList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.similarParent) RelativeLayout parentLayout;
        @BindView(R.id.similarPoster) ImageView imgSimilarPoster;
        @BindView(R.id.similarMovieTitle) TextView txtSimilarMovieTitle;
        @BindView(R.id.similarYear) TextView txtSimilarYear;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.similarParent) public void onMovieClick() {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(Constants.MOVIE_ID, similarMoviesResultList.get(getAdapterPosition()).getId());
            context.startActivity(intent);
        }
    }
}
