package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.other.Const;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.model.sources.MapperDTO;
import com.example.oxionaz.mvpproject.model.sources.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import rx.Observable;

public class DataManager implements Model {

    private EventBus eventBus;

    @Inject
    RestService restService;

    @Inject
    DatabaseHelper databaseHelper;

    public void onCreate(EventBus eventBus){
        App.getAppComponent().inject(this);
        this.eventBus = eventBus;
    }

    // Download data

    @Override
    public Observable<List<Repository>> downloadRepositories(String user) {
        return restService.downloadRepositoryList(user)
                .doOnError(eventBus::onDownloadError)
                .onErrorResumeNext(Observable.empty())
                .doOnNext(repositoryDTOList -> {
                    if (repositoryDTOList == null){
                        eventBus.onDownloadError(Const.DATA_THROWABLE);
                        Observable.empty();
                    }
                })
                .flatMap(Observable::from)
                .map(MapperDTO::mapRepo)
                .toList()
                .doOnNext(databaseHelper::saveRepositories);
    }

    @Override
    public Observable<List<Branch>> downloadBranches(String owner, String repo) {
        return restService.downloadBranchList(owner, repo)
                .doOnError(eventBus::onDownloadError)
                .onErrorResumeNext(Observable.empty())
                .doOnNext(branchDTOList -> {
                    if (branchDTOList == null){
                        eventBus.onDownloadError(Const.DATA_THROWABLE);
                        Observable.empty();
                    }
                })
                .flatMap(Observable::from)
                .map(MapperDTO::mapBranch)
                .toList()
                .doOnNext(databaseHelper::saveBranches);
    }

    @Override
    public Observable<List<Contributor>> downloadContributors(String owner, String repo) {
        return restService.downloadContributorList(owner, repo)
                .doOnError(eventBus::onDownloadError)
                .onErrorResumeNext(Observable.empty())
                .doOnNext(contributorDTOList -> {
                    if (contributorDTOList == null){
                        eventBus.onDownloadError(Const.DATA_THROWABLE);
                        Observable.empty();
                    }
                })
                .flatMap(Observable::from)
                .map(MapperDTO::mapContrib)
                .toList()
                .doOnNext(databaseHelper::saveContributors);
    }

    // Get data from cash

    @Override
    public Observable<List<Repository>> getRepositoriesFromCash() {
        return databaseHelper.getRepositories()
                .doOnError(eventBus::onCashError);
    }

    @Override
    public Observable<List<Branch>> getBranchesFromCash() {
        return databaseHelper.getBranches()
                .doOnError(eventBus::onCashError);
    }

    @Override
    public Observable<List<Contributor>> getContributorsFromCash() {
        return databaseHelper.getContributors()
                .doOnError(eventBus::onCashError);
    }

    // Clear cash

    @Override
    public void clearRepositoryCash() {
        databaseHelper.clearRepositoryTable();
    }

    @Override
    public void clearBranchesCash() {
        databaseHelper.clearBranchTable();
    }

    @Override
    public void clearContributorsCash() {
        databaseHelper.clearContributorTable();
    }
}
