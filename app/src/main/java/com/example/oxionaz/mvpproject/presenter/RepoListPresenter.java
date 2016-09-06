package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragmentView;
import rx.Subscription;

public class RepoListPresenter extends BasePresenter {

    private RepoListFragmentView view;

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
        view.showLoading(true);

        Subscription subscription = dataManager.downloadRepositories(view.getUserName())
                .subscribe(repositories -> {
                    if (checkList(repositories)) {
                        view.showRepoList(repositories);
                        view.showLoading(false);
                    }
                });
        addSubscription(subscription);
    }

}
