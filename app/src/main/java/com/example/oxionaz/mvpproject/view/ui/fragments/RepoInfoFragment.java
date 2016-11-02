package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import javax.inject.Inject;

@EFragment(R.layout.fragment_repo_info)
public class RepoInfoFragment extends BaseFragment implements RepoInfoFragmentView, EventBus {

    @Inject
    protected RepoInfoPresenter repoInfoPresenter;

    @ViewById
    TextView name, description;

    @ViewById
    Button link;

    @ViewById
    RecyclerView branches, contributors;

    public static RepoInfoFragment newInstance(Repository repository) {
        RepoInfoFragment myFragment = new RepoInfoFragment_();
        Bundle args = new Bundle();
        args.putString("owner", repository.getOwner());
        args.putString("name", repository.getName());
        args.putString("desc", repository.getDescription());
        args.putString("url", repository.getHtml_url());
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

    @AfterViews
    void ready(){
        branches.setHasFixedSize(true);
        contributors.setHasFixedSize(true);
        branches.setLayoutManager(new LinearLayoutManager(getContext()));
        contributors.setLayoutManager(new LinearLayoutManager(getContext()));
        getActivity().setTitle(getArguments().getString("owner"));
        name.setText(getArguments().getString("name"));
        if (getArguments().getString("desc") != null)
            description.setText(getArguments().getString("desc"));
        else description.setVisibility(View.GONE);
    }

    @Click
    void link(){
        Uri uriUrl = Uri.parse(getArguments().getString("url"));
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
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
