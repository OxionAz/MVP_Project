package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.App;
import com.example.oxionaz.mvpproject.model.sources.ServiceHelper;
import com.example.oxionaz.mvpproject.model.sources.rest.models.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.RepositoryDTO;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class RestService extends ServiceHelper implements Rest {

    @Inject
    protected RestClient restClient;

    public RestService(){
        App.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<RepositoryDTO>> downloadRepositoryList(String user){
        return restClient.getGitHubAPI().getRepositories(user)
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<BranchDTO>> downloadBranchList(String owner, String repo) {
        return restClient.getGitHubAPI().getBranches(owner, repo)
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<ContributorDTO>> downloadContributorList(String owner, String repo) {
        return restClient.getGitHubAPI().getContributors(owner, repo)
                .compose(applySchedulers());
    }

}
