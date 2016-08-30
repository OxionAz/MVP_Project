package com.example.oxionaz.mvpproject.presenter;

import android.os.Bundle;
import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragmentView;
import com.raizlabs.android.dbflow.structure.Model;
import java.util.ArrayList;
import java.util.List;
import rx.Subscription;

public class RepoInfoPresenter extends BasePresenter {

    private static final String BUNDLE_BRANCHES_KEY = "BUNDLE_BRANCHES_KEY";
    private static final String BUNDLE_CONTRIBUTORS_KEY = "BUNDLE_CONTRIBUTORS_KEY";
    private RepoInfoFragmentView view;
    private List<Branch> branchList;
    private List<Contributor> contributorList;
    private Repository repository;

    public RepoInfoPresenter(RepoInfoFragmentView view, Repository repository, EventBus eventBus) {
        super(eventBus);
        this.view = view;
        this.repository = repository;
    }

    public void downloadInfo(){
        String owner = repository.getOwner();
        String name = repository.getName();

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

    // Saving instance state

    public void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            contributorList = (List<Contributor>) savedInstanceState.getSerializable(BUNDLE_CONTRIBUTORS_KEY);
            branchList = (List<Branch>) savedInstanceState.getSerializable(BUNDLE_BRANCHES_KEY);
        }

        if (contributorList == null || branchList == null) {
            downloadInfo();
        } else {
            view.showBranches(branchList);
            view.showContributors(contributorList);
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        if (checkList(contributorList))
            outState.putSerializable(BUNDLE_CONTRIBUTORS_KEY, new ArrayList<>(contributorList));
        if (checkList(branchList))
            outState.putSerializable(BUNDLE_BRANCHES_KEY, new ArrayList<>(branchList));

    }

    // Check data for exists

    private static <T extends Model> boolean checkList(List<T> data){
        return data != null && !data.isEmpty();
    }

}
