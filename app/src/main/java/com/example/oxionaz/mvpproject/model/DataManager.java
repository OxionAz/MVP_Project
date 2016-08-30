package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.sources.MapperDTO;
import com.example.oxionaz.mvpproject.model.sources.db.DatabaseHelper;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;

import java.util.List;
import rx.Observable;

public class DataManager implements Model {

    private RestService restService = new RestService();
    private DatabaseHelper databaseHelper = new DatabaseHelper();
    private EventBus eventBus;

    public DataManager(EventBus eventBus){
        this.eventBus = eventBus;
    }

    // Download data

    @Override
    public Observable<List<Repository>> downloadRepositories(String user) {
        return restService.downloadRepositoryList(user)
                .doOnError(throwable -> eventBus.onDownloadError(throwable.getMessage()))
                .onErrorResumeNext(Observable.empty())
                .flatMap(Observable::from)
                .map(MapperDTO::mapRepo)
                .toList()
                .doOnNext(databaseHelper::saveRepositories);
    }

    @Override
    public Observable<List<Branch>> downloadBranches(String owner, String repo) {
        return restService.downloadBranchList(owner, repo)
                .doOnError(throwable -> eventBus.onDownloadError(throwable.getMessage()))
                .onErrorResumeNext(Observable.empty())
                .flatMap(Observable::from)
                .map(MapperDTO::mapBranch)
                .toList()
                .doOnNext(databaseHelper::saveBranches);
    }

    @Override
    public Observable<List<Contributor>> downloadContributors(String owner, String repo) {
        return restService.downloadContributorList(owner, repo)
                .doOnError(throwable -> eventBus.onDownloadError(throwable.getMessage()))
                .onErrorResumeNext(Observable.empty())
                .flatMap(Observable::from)
                .map(MapperDTO::mapContrib)
                .toList()
                .doOnNext(databaseHelper::saveContributors);
    }

    // Get data from cash

    @Override
    public Observable<List<Repository>> getRepositoriesFromCash() {
        return databaseHelper.getRepositories()
                .doOnError(throwable -> eventBus.onCashError(throwable.getMessage()));
    }

    @Override
    public Observable<List<Branch>> getBranchesFromCash() {
        return databaseHelper.getBranches()
                .doOnError(throwable -> eventBus.onCashError(throwable.getMessage()));
    }

    @Override
    public Observable<List<Contributor>> getContributorsFromCash() {
        return databaseHelper.getContributors()
                .doOnError(throwable -> eventBus.onCashError(throwable.getMessage()));
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
