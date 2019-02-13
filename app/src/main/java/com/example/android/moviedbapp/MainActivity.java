package com.example.android.moviedbapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.moviedbapp.popular.PopularFragment;
import com.example.android.moviedbapp.popular.PopularMovieAdapter;
import com.example.android.moviedbapp.popular.PopularModel;
import com.example.android.moviedbapp.popular.PopularResult;
import com.example.android.moviedbapp.top_rated.TopRatedFragment;
import com.example.android.moviedbapp.upcoming.UpcomingFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout)findViewById(R.id.tablayoutId);
        viewPager = (ViewPager)findViewById(R.id.viewPagerId);

        //Add fragments
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(3);

        viewPagerAdapter.addFragment(new PopularFragment(), "Popular");
        viewPagerAdapter.addFragment(new TopRatedFragment(), "Top rated");
        viewPagerAdapter.addFragment(new UpcomingFragment(), "Upcoming");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



    }
}
