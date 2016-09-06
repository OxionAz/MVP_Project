package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;
import rx.Subscription;

public class RepoInfoPresenter extends BasePresenter {

    private RepoInfoFragmentView view;

    public RepoInfoPresenter(RepoInfoFragmentView view, EventBus eventBus) {
        super(eventBus);
        this.view = view;
    }

    // Operations with Info data

    public void downloadInfo(String owner, String name){
        Subscription subBranches = dataManager.downloadBranches(owner, name)
                .subscribe(repositories -> {
                    if (checkList(repositories)) {
                        view.showBranches(repositories);
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
                .subscribe(repositories -> {
                    if (checkList(repositories)) {
                        view.showBranches(repositories);
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
