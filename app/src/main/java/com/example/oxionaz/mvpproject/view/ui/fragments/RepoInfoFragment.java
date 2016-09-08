package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private RepoInfoPresenter repoInfoPresenter = new RepoInfoPresenter(this, this);

    @ViewById
    TextView name;

    @ViewById
    RecyclerView branches, contributors;

    public static RepoInfoFragment newInstance(Repository repository) {
        RepoInfoFragment myFragment = new RepoInfoFragment_();
        Bundle args = new Bundle();
        args.putString("owner", repository.getOwner());
        args.putString("name", repository.getName());
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        repoInfoPresenter.onCreate(savedInstanceState, getArguments().getString("owner"), getArguments().getString("name"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        repoInfoPresenter.onSaveInstanceState(outState);
    }

    @AfterViews
    void ready(){
        branches.setHasFixedSize(true);
        contributors.setHasFixedSize(true);
        branches.setLayoutManager(new LinearLayoutManager(getContext()));
        contributors.setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().setTitle(getArguments().getString("owner"));
        name.setText(getArguments().getString("name"));
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
    public void onDownloadError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCashError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    protected BasePresenter getPresenter() {
        return repoInfoPresenter;
    }

}
