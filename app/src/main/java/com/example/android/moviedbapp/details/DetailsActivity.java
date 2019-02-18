package com.example.android.moviedbapp.details;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.R;
import com.example.android.moviedbapp.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    ImageView imgBackdrop;
    ImageView imgPoster;
    TextView txtDate;
    TextView txtDuration;
    TextView txtGenres;
    TextView txtMovieTitle;
    TextView txtMovieDescription;
    TextView txtOriginalTitle;
    TextView txtOriginalLanguage;
    TextView txtBudget;
    TextView txtHomepage;
    TextView txtDetailAverage;
    TextView txtDetailVoteCount;
    TextView txtDetailPopularity;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    //TextView txtReleases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        linearLayout = (LinearLayout)findViewById(R.id.mainLayout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imgBackdrop = (ImageView)findViewById(R.id.imgBackdropPath);
        imgPoster = (ImageView)findViewById(R.id.imgPoster);
        txtDate = (TextView)findViewById(R.id.mdReleaseDate);
        txtDuration = (TextView) findViewById(R.id.mdDuration);
        txtGenres = (TextView) findViewById(R.id.mdGenres);
        txtMovieTitle = (TextView) findViewById(R.id.mdMovieTitle);
        txtMovieDescription = (TextView)findViewById(R.id.mdDescription);
        txtOriginalTitle = (TextView)findViewById(R.id.txtOriginaltitle);
        txtOriginalLanguage = (TextView)findViewById(R.id.txtOriginalLanguage);
        txtBudget = (TextView)findViewById(R.id.txtBudget);
        txtHomepage = (TextView)findViewById(R.id.txtHomepage);
        txtDetailAverage = (TextView)findViewById(R.id.detailVoteAverage);
        txtDetailVoteCount = (TextView)findViewById(R.id.detailVoteCount);
        txtDetailPopularity = (TextView)findViewById(R.id.detailPopularity);
       // txtReleases = (TextView)findViewById(R.id.txtReleases);


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
            int id = bundle.getInt("MOVIE_ID");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieApiHandler movieApiHandler = retrofit.create(MovieApiHandler.class);
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
                    String date = Util.getYearFromDate(movieDetailsModel.getReleaseDate());
                    txtDate.setText(" • " + date + " • ");
                    String time = Util.convertRuntime(movieDetailsModel.getRuntime());
                    txtDuration.setText(String.valueOf(time));
                    txtMovieTitle.setText(movieDetailsModel.getTitle());
                    txtMovieDescription.setText(movieDetailsModel.getOverview());
                    txtOriginalTitle.setText(movieDetailsModel.getOriginalTitle());
                    txtOriginalLanguage.setText(movieDetailsModel.getSpokenLanguages().get(0).getName());
                    txtBudget.setText("$" +String.valueOf(movieDetailsModel.getBudget()));
                    txtHomepage.setText(movieDetailsModel.getHomepage());
                    txtDetailVoteCount.setText(String.valueOf(movieDetailsModel.getVoteCount()));
                    txtDetailAverage.setText(String.valueOf(movieDetailsModel.getVoteAverage()));
                    txtDetailPopularity.setText(String.valueOf(movieDetailsModel.getPopularity()));
                    String posterUrl ="https://image.tmdb.org/t/p/w500/" + movieDetailsModel.getPosterPath();
                    Picasso.get().load(posterUrl).fit().centerCrop().into(imgPoster);
                    String backDropUrl ="https://image.tmdb.org/t/p/w500/" + movieDetailsModel.getBackdropPath();
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
