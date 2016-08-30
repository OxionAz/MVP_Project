package com.example.oxionaz.mvpproject.presenter;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragmentView;
import com.raizlabs.android.dbflow.structure.Model;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class RepoListPresenter extends BasePresenter {

    private static final String BUNDLE_REPO_LIST_KEY = "BUNDLE_REPO_LIST_KEY";
    private RepoListFragmentView view;
    private List<Repository> repoList;

    public RepoListPresenter(RepoListFragmentView view, EventBus eventBus){
        super(eventBus);
        this.view = view;
    }

    // Operations with Repo data

    public void clearRepoCash() {
        dataManager.clearRepositoryCash();
    }

    public void getRepoFromCash() {
        Subscription subscription = dataManager.getRepositoriesFromCash()
                .subscribe(repositories -> {
                    if (checkList(repositories)) {
                        view.showRepoList(repositories);
                    }
                });
        addSubscription(subscription);
    }

    public void onSearchClick() {
        Subscription subscription = dataManager.downloadRepositories(view.getUserName())
                .subscribe(repositories -> {
                    if (checkList(repositories)) {
                        view.showRepoList(repositories);
                    }
                });
        addSubscription(subscription);
    }

    // Saving instance state

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            repoList = (List<Repository>) savedInstanceState.getSerializable(BUNDLE_REPO_LIST_KEY);
        }
        if (checkList(repoList)) {
            view.showRepoList(repoList);
        }
    }

    public void onSaveInstanceState(Bundle outState){
        outState.putSerializable(BUNDLE_REPO_LIST_KEY, new ArrayList<>(repoList));
    }

    // Check data for exists

    private static <T extends Model> boolean checkList(List<T> data){
        return data != null && !data.isEmpty();
    }

}
