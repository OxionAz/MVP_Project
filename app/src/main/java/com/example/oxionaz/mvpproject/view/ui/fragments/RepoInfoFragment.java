package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;
import com.example.oxionaz.mvpproject.presenter.RepoInfoPresenter;
import com.example.oxionaz.mvpproject.view.ui.adapters.BranchAdapter;
import com.example.oxionaz.mvpproject.view.ui.adapters.ContributorAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

@EFragment(R.layout.fragment_repo_info)
public class RepoInfoFragment extends BaseFragment implements RepoInfoFragmentView, EventBus {

    private static final String BUNDLE_REPO_KEY = "BUNDLE_REPO_KEY";
    private RepoInfoPresenter repoInfoPresenter;

    @ViewById
    TextView name;

    @ViewById
    RecyclerView branches, contributors;

    @AfterViews
    void ready(){
        repoInfoPresenter = new RepoInfoPresenter(this, getRepositoryVO(), this);
        branches.setHasFixedSize(true);
        contributors.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        branches.setLayoutManager(linearLayoutManager);
        contributors.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getRepositoryVO().getOwner());
        name.setText(getRepositoryVO().getName());
    }

    public static RepoInfoFragment newInstance(Repository repository) {
        RepoInfoFragment myFragment = new RepoInfoFragment_();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_REPO_KEY, (Serializable) repository);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        repoInfoPresenter.onCreate(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        repoInfoPresenter.onSaveInstanceState(outState);
    }

    private Repository getRepositoryVO() {
        return (Repository) getArguments().getSerializable(BUNDLE_REPO_KEY);
    }

    @Override
    public void showContributors(List<Contributor> con) {
        ContributorAdapter contributorAdapter = new ContributorAdapter(con);
        contributors.setAdapter(contributorAdapter);
    }

    @Override
    public void showBranches(List<Branch> bran) {
        BranchAdapter branchAdapter = new BranchAdapter(bran);
        branches.setAdapter(branchAdapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return repoInfoPresenter;
    }

    @Override
    public void onDownloadError(String error) {

    }

    @Override
    public void onCashError(String error) {

    }
}
