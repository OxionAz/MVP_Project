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
import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;
import com.example.oxionaz.mvpproject.presenter.RepoInfoPresenter;
import com.example.oxionaz.mvpproject.view.ui.adapters.BranchAdapter;
import com.example.oxionaz.mvpproject.view.ui.adapters.ContributorAdapter;
import java.util.List;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoInfoFragment extends BaseFragment implements RepoInfoFragmentView, EventBus {

    @Inject
    protected RepoInfoPresenter repoInfoPresenter;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.branches)
    RecyclerView branches;

    @BindView(R.id.contributors)
    RecyclerView contributors;

    public static RepoInfoFragment newInstance(Repository repository) {
        RepoInfoFragment myFragment = new RepoInfoFragment();
        Bundle args = new Bundle();
        args.putString("owner", repository.getOwner());
        args.putString("name", repository.getName());
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        repoInfoPresenter.onCreate(this, this);
        if (savedInstanceState == null)
            repoInfoPresenter.downloadInfo(
                    getArguments().getString("owner"), getArguments().getString("name"));
        else repoInfoPresenter.getInfoFromCash();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_info, container, false);
        ButterKnife.bind(this, view);

        branches.setHasFixedSize(true);
        contributors.setHasFixedSize(true);
        branches.setLayoutManager(new LinearLayoutManager(getContext()));
        contributors.setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().setTitle(getArguments().getString("owner"));
        name.setText(getArguments().getString("name"));

        return view;
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
    public void onDownloadError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCashError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    protected BasePresenter getPresenter() {
        return repoInfoPresenter;
    }

}
