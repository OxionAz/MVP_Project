package com.example.oxionaz.mvpproject.presenter;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.util.CheckUtils;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;
import java.util.List;
import rx.Subscriber;
import rx.Subscription;

public class RepoInfoPresenter extends BasePresenter {

    private RepoInfoFragmentView view;
    private EventBus eventBus;

    public void onCreate(RepoInfoFragmentView view, EventBus eventBus){
        dataManager.onCreate(eventBus);
        this.view = view;
        this.eventBus = eventBus;
    }

    // Operations with Info data

    public void downloadInfo(String owner, String name){
        Subscription subBranches = dataManager.downloadBranches(owner, name)
                .subscribe(new Subscriber<List<Branch>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        eventBus.onDownloadError(e);
                    }

                    @Override
                    public void onNext(List<Branch> branches) {
                        if (CheckUtils.checkList(branches)) {
                            view.showBranches(branches);
                        }
                    }
                });
        addSubscription(subBranches);

        Subscription subContributors = dataManager.downloadContributors(owner, name)
                .subscribe(new Subscriber<List<Contributor>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        eventBus.onDownloadError(e);
                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        if (CheckUtils.checkList(contributors)) {
                            view.showContributors(contributors);
                        }
                    }
                });
        addSubscription(subContributors);
    }

    public void getInfoFromCash(){
        Subscription subBranches = dataManager.getBranchesFromCash()
                .subscribe(branches -> {
                    if (CheckUtils.checkList(branches)) {
                        view.showBranches(branches);
                    }
                });
        addSubscription(subBranches);

        Subscription subContributors = dataManager.getContributorsFromCash()
                .subscribe(contributors -> {
                    if (CheckUtils.checkList(contributors)) {
                        view.showContributors(contributors);
                    }
                });
        addSubscription(subContributors);
    }
}
