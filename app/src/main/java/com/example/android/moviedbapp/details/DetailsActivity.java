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

import com.example.android.moviedbapp.Constants;
import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.NetworkSourceData;
import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

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
            int id = bundle.getInt(Constants.MOVIE_ID);
            MovieApiHandler movieApiHandler = NetworkSourceData.getInstance().getRetrofit().create(MovieApiHandler.class);
            Call<MovieDetailsModel> call = movieApiHandler.getMovieDetails(String.valueOf(id));
            call.enqueue(new Callback<MovieDetailsModel>() {
                @Override
                public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {

                    if (!response.isSuccessful()) {
                        return;
                    }

                    MovieDetailsModel movieDetailsModel = response.body();
                    List<Genre> genres = movieDetailsModel.getGenres();
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

                    txtDate.setText(Constants.SPACED_BULLET + Util.getYearFromDate(movieDetailsModel.getReleaseDate()) + Constants.SPACED_BULLET);
                    txtDuration.setText(String.valueOf(Util.convertRuntime(movieDetailsModel.getRuntime())));
                    txtMovieTitle.setText(movieDetailsModel.getTitle());
                    txtMovieDescription.setText(movieDetailsModel.getOverview());
                    txtOriginalTitle.setText(movieDetailsModel.getOriginalTitle());
                    txtOriginalLanguage.setText(movieDetailsModel.getSpokenLanguages().get(0).getName());
                    txtBudget.setText(Constants.PRICE_SYMBOL + String.valueOf(movieDetailsModel.getBudget()));
                    txtHomepage.setText(movieDetailsModel.getHomepage());
                    txtDetailVoteCount.setText(String.valueOf(movieDetailsModel.getVoteCount()));
                    txtDetailAverage.setText(String.valueOf(movieDetailsModel.getVoteAverage()));
                    txtDetailPopularity.setText(String.valueOf(movieDetailsModel.getPopularity()));
                    String posterUrl = Constants.POSTER_URL + movieDetailsModel.getPosterPath();
                    Picasso.get().load(posterUrl).fit().centerCrop().into(imgPoster);
                    String backDropUrl = Constants.BACKDROP_URL + movieDetailsModel.getBackdropPath();
                    Picasso.get().load(backDropUrl).fit().centerCrop().into(imgBackdrop);
                    linearLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
