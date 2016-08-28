package com.example.oxionaz.mvpproject.model.rest;

import com.example.oxionaz.mvpproject.model.rest.api.GitHubAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private Retrofit retrofit;

    public RestClient(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging);
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(httpClient.build())
                .build();
    }

    public GitHubAPI getGitHubAPI(){
        return retrofit.create(GitHubAPI.class);
    }
}
