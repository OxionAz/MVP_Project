package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.other.Const;
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
                .baseUrl(Const.BASE_URL)
                .client(httpClient.build())
                .build();
    }

    public GitHubAPI getGitHubAPI(){
        return retrofit.create(GitHubAPI.class);
    }
}
