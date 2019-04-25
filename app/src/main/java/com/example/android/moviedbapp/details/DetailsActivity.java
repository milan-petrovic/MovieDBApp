package com.example.android.moviedbapp.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.example.android.moviedbapp.popular.PopularMovieAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.similarRecycleView) RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Intent shareIntent;
    private ShareActionProvider share;
    private Bundle bundle;

    private DetailsPresenter detailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailsPresenter = new DetailsPresenter();
        detailsPresenter.setView(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            detailsPresenter.getMovieDetails(bundle.getInt(Constants.MOVIE_ID));
        }
    }

    @OnClick(R.id.txtHomepage)
    public void openMovieHomepage() {
        String url = txtHomepage.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
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
    public void showSimilarMoviesMainDetails(SimilarMovies similarMovies) {
        adapter = new SimilarMovieAdapter(this, similarMovies.getResults());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
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
        if (movieDetailsModel.getRuntime() != null) {
            txtDuration.setText(String.valueOf(Util.convertRuntime(movieDetailsModel.getRuntime())));
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        share = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_item_share));
        shareIntent = new Intent();
        makeIntentShareable(shareIntent);
        share.setShareIntent(shareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_share:
                startActivity(shareIntent);
                break;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    public void makeIntentShareable(Intent intent) {
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_MOVIE_LINK + bundle.getInt(Constants.MOVIE_ID));
        intent.setType("text/plain");
    }
}
