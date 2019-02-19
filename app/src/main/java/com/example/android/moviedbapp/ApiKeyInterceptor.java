package com.example.android.moviedbapp;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiKeyInterceptor  implements Interceptor {

    private final String API_KEY = "ecfe957f81c6a84027d326ccd2cd19fd";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = attachApiKeyAsQueryParam(chain);
        return chain.proceed(requestBuilder.build());
    }

    private Request.Builder attachApiKeyAsQueryParam(Chain chain) {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build();
        return original.newBuilder().url(url);
    }
}
