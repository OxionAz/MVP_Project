package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.DataManager;
import com.example.oxionaz.mvpproject.view.View;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class RepoListPresenter implements Presenter {

    private Subscription subscription = Subscriptions.empty();
    private DataManager dataManager;
    private View view;

    public RepoListPresenter(View view, EventBus eventBus){
        this.view = view;
        this.dataManager = new DataManager(eventBus);
    }

    @Override
    public void clearData() {
        dataManager.clearRepositoryCash();
    }

    @Override
    public void tryGetDataFromDB() {
        checkSubscription();
        subscription = dataManager.getRepositoriesFromCash()
                .subscribe(infos -> {
                    if (infos != null && !infos.isEmpty()) {
                        view.showList(infos);
                    }
                });
    }

    @Override
    public void onSearchClick() {
        checkSubscription();
        subscription = dataManager.downloadRepositories(view.getUserName())
                .subscribe(infos -> {
                    if (infos != null && !infos.isEmpty()) {
                        view.showList(infos);
                    }
                });
    }

    @Override
    public void onStop() {
        checkSubscription();
    }

    private void checkSubscription(){
        if (!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
