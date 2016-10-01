package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.util.CheckUtils;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragmentView;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;

public class RepoListPresenter extends BasePresenter {

    private RepoListFragmentView view;
    private EventBus eventBus;

    public void onCreate(RepoListFragmentView view, EventBus eventBus){
        dataManager.onCreate(eventBus);
        this.view = view;
        this.eventBus = eventBus;
    }

    // Operations with Repo data

    public void clearRepoCash() {
        dataManager.clearRepositoryCash();
    }

    public void getRepoFromCash() {
        Subscription subscription = dataManager.getRepositoriesFromCash()
                .subscribe(repositories -> {
                    if (CheckUtils.checkList(repositories)) {
                        view.showRepoList(repositories);
                    }
                });
        addSubscription(subscription);
    }

    public void onSearchClick() {
        view.showLoading(true);

        Subscription subscription = dataManager.downloadRepositories(view.getUserName())
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        eventBus.onDownloadError(e);
                        view.showLoading(false);
                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        if (CheckUtils.checkList(repositories)) {
                            view.showRepoList(repositories);
                            view.showLoading(false);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
