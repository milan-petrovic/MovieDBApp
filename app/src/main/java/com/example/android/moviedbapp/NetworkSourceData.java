package com.example.android.moviedbapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkSourceData {

    private static NetworkSourceData instance;
    private  Retrofit retrofit;

    private NetworkSourceData() {
        buildRetrofit();
    }

    public void buildRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient())
                .build();
    }

    public OkHttpClient buildOkHttpClient() {
        return new OkHttpClient.Builder().addNetworkInterceptor(new ApiKeyInterceptor()).build();
    }

    public static NetworkSourceData getInstance() {
        if (instance == null) {
            synchronized (NetworkSourceData.class) {
                if (instance == null) {
                    instance = new NetworkSourceData();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
