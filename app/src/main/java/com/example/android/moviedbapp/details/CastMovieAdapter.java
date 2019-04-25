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

public class CastMovieAdapter extends RecyclerView.Adapter<CastMovieAdapter.MovieViewHolder>{
    private List<Cast> castResult;
    private Context context;
    private Cast movieCast;

    public CastMovieAdapter(Context context, List<Cast> resultList) {
        this.context = context;
        this.castResult = resultList;
    }

    @NonNull
    @Override
    public CastMovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cast_item, viewGroup, false);
        CastMovieAdapter.MovieViewHolder mvh = new CastMovieAdapter.MovieViewHolder(v);
        return  mvh;    }

    @Override
    public void onBindViewHolder(@NonNull CastMovieAdapter.MovieViewHolder movieViewHolder, int i) {
        movieCast = castResult.get(i);
        movieViewHolder.txtCastName.setText(movieCast.getName());
        movieViewHolder.txtCastCharacter.setText("as " + movieCast.getCharacter());
        String imageUrl = Constants.POSTER_URL + movieCast.getProfilePath();
        Picasso.get().load(imageUrl).fit().centerCrop().into(movieViewHolder.imgCastPoster);
    }

    @Override
    public int getItemCount() {
        return castResult.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.castParent)
        RelativeLayout parentLayout;
        @BindView(R.id.castPoster)
        ImageView imgCastPoster;
        @BindView(R.id.castName)
        TextView txtCastName;
        @BindView(R.id.castCharacter) TextView txtCastCharacter;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
