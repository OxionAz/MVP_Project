package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.model.sources.ServiceHelper;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class RestService extends ServiceHelper implements Rest {

    @Inject
    GitHubAPI gitHubAPI;

    public RestService(){
        App.getAppComponent().inject(this);
    }

    @Override
    public Observable<List<RepositoryDTO>> downloadRepositoryList(String user){
        return gitHubAPI.getRepositories(user)
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<BranchDTO>> downloadBranchList(String owner, String repo) {
        return gitHubAPI.getBranches(owner, repo)
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<ContributorDTO>> downloadContributorList(String owner, String repo) {
        return gitHubAPI.getContributors(owner, repo)
                .compose(applySchedulers());
    }
}
