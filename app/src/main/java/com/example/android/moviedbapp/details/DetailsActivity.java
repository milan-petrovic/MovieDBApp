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

public class DetailsActivity extends AppCompatActivity implements DetailsPresenter.View {

    private ImageView imgBackdrop;
    private ImageView imgPoster;
    private TextView txtDate;
    private TextView txtDuration;
    private TextView txtGenres;
    private TextView txtMovieTitle;
    private TextView txtMovieDescription;
    private TextView txtOriginalTitle;
    private TextView txtOriginalLanguage;
    private TextView txtBudget;
    private TextView txtHomepage;
    private TextView txtDetailAverage;
    private TextView txtDetailVoteCount;
    private TextView txtDetailPopularity;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        linearLayout = findViewById(R.id.mainLayout);
        progressBar = findViewById(R.id.progressBar);
        imgBackdrop = findViewById(R.id.imgBackdropPath);
        imgPoster = findViewById(R.id.imgPoster);
        txtDate = findViewById(R.id.mdReleaseDate);
        txtDuration = findViewById(R.id.mdDuration);
        txtGenres = findViewById(R.id.mdGenres);
        txtMovieTitle = findViewById(R.id.mdMovieTitle);
        txtMovieDescription = findViewById(R.id.mdDescription);
        txtOriginalTitle = findViewById(R.id.txtOriginaltitle);
        txtOriginalLanguage = findViewById(R.id.txtOriginalLanguage);
        txtBudget = findViewById(R.id.txtBudget);
        txtHomepage = findViewById(R.id.txtHomepage);
        txtDetailAverage = findViewById(R.id.detailVoteAverage);
        txtDetailVoteCount = findViewById(R.id.detailVoteCount);
        txtDetailPopularity = findViewById(R.id.detailPopularity);
        detailsPresenter = new DetailsPresenter();
        detailsPresenter.setView(this);

        txtHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = txtHomepage.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
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
    public void showMovieGengres(List<Genre> genres) {
        String genre = "";
        for (Genre g : genres) {
            genre = txtGenres.getText().toString();
            if (TextUtils.isEmpty(genre)) {
                genre = g.getName();
                txtGenres.setText(genre);
            } else {
                genre = genre + ", " + g.getName();
                txtGenres.setText(genre);
            }
        }
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
