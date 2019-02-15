package com.example.android.moviedbapp.details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviedbapp.MovieApiHandler;
import com.example.android.moviedbapp.R;
import com.squareup.picasso.Picasso;

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
    //TextView txtReleases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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
                    txtDate.setText(movieDetailsModel.getReleaseDate());
                    txtDuration.setText(String.valueOf(movieDetailsModel.getRuntime()));
                    txtMovieTitle.setText(movieDetailsModel.getTitle());
                    txtMovieDescription.setText(movieDetailsModel.getOverview());
                    txtOriginalTitle.setText(movieDetailsModel.getOriginalTitle());
                    txtOriginalLanguage.setText(movieDetailsModel.getOriginalLanguage());
                    txtBudget.setText("$" +String.valueOf(movieDetailsModel.getBudget()));
                    txtHomepage.setText(movieDetailsModel.getHomepage());
                    txtDetailVoteCount.setText(String.valueOf(movieDetailsModel.getVoteCount()));
                    txtDetailAverage.setText(String.valueOf(movieDetailsModel.getVoteAverage()));
                    txtDetailPopularity.setText(String.valueOf(movieDetailsModel.getPopularity()));
                    String posterUrl ="https://image.tmdb.org/t/p/w500/" + movieDetailsModel.getPosterPath();
                    Picasso.get().load(posterUrl).fit().centerCrop().into(imgPoster);
                    String backDropUrl ="https://image.tmdb.org/t/p/w500/" + movieDetailsModel.getBackdropPath();
                    Picasso.get().load(backDropUrl).fit().centerCrop().into(imgBackdrop);
                }

                @Override
                public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }


    }
}
