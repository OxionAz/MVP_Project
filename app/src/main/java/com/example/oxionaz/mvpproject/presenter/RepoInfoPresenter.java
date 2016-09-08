package com.example.oxionaz.mvpproject.presenter;

import android.os.Bundle;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.parselable.InfoP;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class RepoInfoPresenter extends BasePresenter {

    private static final String BUNDLE_INFO = "BUNDLE_INFO";

    private RepoInfoFragmentView view;
    private List<Contributor> contributorList;
    private List<Branch> branchList;

    public RepoInfoPresenter(RepoInfoFragmentView view, EventBus eventBus) {
        super(eventBus);
        this.view = view;
    }

    // Operations with Info data

    public void downloadInfo(String owner, String name){
        Subscription subBranches = dataManager.downloadBranches(owner, name)
                .subscribe(branches -> {
                    if (checkList(branches)) {
                        view.showBranches(branches);
                        branchList = branches;
                    }
                });
        addSubscription(subBranches);

        Subscription subContributors = dataManager.downloadContributors(owner, name)
                .subscribe(contributors -> {
                    if (checkList(contributors)) {
                        view.showContributors(contributors);
                        contributorList = contributors;
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

    // Save Info data by parcelable (Example)

    public void onCreate(Bundle savedInstanceState, String owner, String name) {

        if (savedInstanceState != null) {
            InfoP infoP = savedInstanceState.getParcelable(BUNDLE_INFO);
            assert infoP != null;
            branchList = infoP.branchList;
            contributorList = infoP.contributorList;
        }

        if (contributorList == null || branchList == null) {
            downloadInfo(owner, name);
        } else {
            view.showBranches(branchList);
            view.showContributors(contributorList);
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        if (contributorList != null && branchList != null)
            outState.putParcelable(BUNDLE_INFO, new InfoP(branchList, contributorList));
    }

}
