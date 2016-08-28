package com.example.oxionaz.mvpproject.model.rest.api;

import com.example.oxionaz.mvpproject.model.rest.models.Repo;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubAPI {

    @GET("users/{user}/repos")
    Observable<List<Repo>> getListRepo(@Path("user") String user);
}