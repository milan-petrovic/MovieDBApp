package com.example.android.moviedbapp.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsPresenter.View {

    @BindView(R.id.imgBackdropPath) ImageView imgBackdrop;
    @BindView(R.id.imgPoster) ImageView imgPoster;
    @BindView(R.id.mdReleaseDate) TextView txtDate;
    @BindView(R.id.mdDuration) TextView txtDuration;
    @BindView(R.id.mdGenres) TextView txtGenres;
    @BindView(R.id.mdMovieTitle) TextView txtMovieTitle;
    @BindView(R.id.mdDescription) TextView txtMovieDescription;
    @BindView(R.id.txtOriginaltitle) TextView txtOriginalTitle;
    @BindView(R.id.txtOriginalLanguage) TextView txtOriginalLanguage;
    @BindView(R.id.txtBudget) TextView txtBudget;
    @BindView(R.id.txtHomepage) TextView txtHomepage;
    @BindView(R.id.detailVoteAverage) TextView txtDetailAverage;
    @BindView(R.id.detailVoteCount) TextView txtDetailVoteCount;
    @BindView(R.id.detailPopularity) TextView txtDetailPopularity;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.mainLayout) LinearLayout linearLayout;
    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        detailsPresenter = new DetailsPresenter();
        detailsPresenter.setView(this);

        txtHomepage.setOnClickListener(v -> {
            String url = txtHomepage.getText().toString();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailsPresenter.getMovieDetails(bundle.getInt(Constants.MOVIE_ID));
        }
    }

    @Override
    public void showProgress() {
        linearLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMovieDescription(String overview) {
        txtMovieDescription.setText(overview);
    }

    @Override
    public void showMovieGengres(String genre) {
        txtGenres.setText(genre);
    }

    @Override
    public void showMovieFacts(MovieDetailsModel movieDetailsModel) {
        txtOriginalTitle.setText(movieDetailsModel.getOriginalTitle());
        txtOriginalLanguage.setText(movieDetailsModel.getSpokenLanguages().get(0).getName());
        txtBudget.setText(Constants.PRICE_SYMBOL + String.valueOf(movieDetailsModel.getBudget()));
        txtHomepage.setText(movieDetailsModel.getHomepage());
    }

    @Override
    public void showMovieRates(MovieDetailsModel movieDetailsModel) {
        txtDetailVoteCount.setText(String.valueOf(movieDetailsModel.getVoteCount()));
        txtDetailAverage.setText(String.valueOf(movieDetailsModel.getVoteAverage()));
        txtDetailPopularity.setText(String.valueOf(movieDetailsModel.getPopularity()));
    }

    @Override
    public void showMovieMainDetails(MovieDetailsModel movieDetailsModel) {
        txtDate.setText(Constants.SPACED_BULLET + Util.getYearFromDate(movieDetailsModel.getReleaseDate()) + Constants.SPACED_BULLET);
        txtDuration.setText(String.valueOf(Util.convertRuntime(movieDetailsModel.getRuntime())));
        txtMovieTitle.setText(movieDetailsModel.getTitle());
    }

    @Override
    public void showMoviePoster(String posterUrl) {
        Picasso.get().load(posterUrl).fit().centerCrop().into(imgPoster);
    }

    @Override
    public void showMovieBackdrop(String backdropUrl) {
        Picasso.get().load(backdropUrl).fit().centerCrop().into(imgBackdrop);
    }

    @Override
    public void displayError() {
        Toast.makeText(this, getString(R.string.problem_with_loading), Toast.LENGTH_SHORT).show();
    }
}
