package com.example.oxionaz.mvpproject.model.sources.rest.api;

import com.example.oxionaz.mvpproject.model.sources.rest.models.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.RepositoryDTO;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubAPI {

    @GET("users/{user}/repos")
    Observable<List<RepositoryDTO>> getRepositories(@Path("user") String owner);

    @GET("repos/{owner}/{repo}/contributors")
    Observable<List<ContributorDTO>> getContributors(@Path("owner") String owner, @Path("repo") String repo);

    @GET("repos/{owner}/{repo}/branches")
    Observable<List<BranchDTO>> getBranches(@Path("owner") String owner, @Path("repo") String repo);
}