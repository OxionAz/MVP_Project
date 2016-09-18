package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;
import rx.Subscription;

public class RepoInfoPresenter extends BasePresenter {

    private RepoInfoFragmentView view;

    public void onCreate(RepoInfoFragmentView view, EventBus eventBus){
        dataManager.onCreate(eventBus);
        this.view = view;
    }

    // Operations with Info data

    public void downloadInfo(String owner, String name){
        Subscription subBranches = dataManager.downloadBranches(owner, name)
                .subscribe(branches -> {
                    if (checkList(branches)) {
                        view.showBranches(branches);
                    }
                });
        addSubscription(subBranches);

        Subscription subContributors = dataManager.downloadContributors(owner, name)
                .subscribe(contributors -> {
                    if (checkList(contributors)) {
                        view.showContributors(contributors);
                    }
                });
        addSubscription(subContributors);
    }

    public void getInfoFromCash(){
        Subscription subBranches = dataManager.getBranchesFromCash()
                .subscribe(branches -> {
                    if (checkList(branches)) {
                        view.showBranches(branches);
                    }
                });
        addSubscription(subBranches);

        Subscription subContributors = dataManager.getContributorsFromCash()
                .subscribe(contributors -> {
                    if (checkList(contributors)) {
                        view.showContributors(contributors);
                    }
                });
        addSubscription(subContributors);
    }

}
