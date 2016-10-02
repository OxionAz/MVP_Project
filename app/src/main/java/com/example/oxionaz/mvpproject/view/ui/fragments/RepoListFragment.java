package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.oxionaz.mvpproject.other.App;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.view.ui.activities.ActivityCallback;
import com.example.oxionaz.mvpproject.view.ui.adapters.RepoAdapter;
import com.example.oxionaz.mvpproject.view.ui.fragments.vh.RepoListVH;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepoListFragment extends BaseFragment implements RepoListFragmentView, EventBus {

    private ActivityCallback activityCallback;
    private RepoListVH repoListVH;
    private MenuItem option_change;

    @Inject
    RepoListPresenter repoListPresenter;

    @BindView(R.id.repo_list)
    RecyclerView repo_list;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    @BindView(R.id.login_field)
    EditText login_field;

    @BindView(R.id.confirm_button)
    Button confirm_button;

    @BindView(R.id.error_text)
    TextView error_text;

    @BindView(R.id.info_text)
    TextView info_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ButterKnife.bind(this, view);

        repoListVH = new RepoListVH(getActivity(), repo_list, progress_bar, login_field, confirm_button, error_text, info_text, option_change);
        activityCallback = (ActivityCallback) getActivity();
        repoListPresenter.onCreate(this, this);
        repoListPresenter.getRepoFromCash();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
        option_change = menu.findItem(R.id.option_change);
        option_change.setOnMenuItemClickListener(menuItem -> {
            option_change();
            return true;
        });
    }

    private void option_change(){
        getActivity().setTitle(this.getString(R.string.app_name));
        repoListVH.onChangeClick();
        repoListPresenter.clearRepoCash();
    }

    @OnClick(R.id.confirm_button)
    void confirm_button(){
        repoListPresenter.onSearchClick();
        repoListVH.onSearchCLick();
    }

    @Override
    public void showRepoList(List<Repository> repoList) {
        getActivity().setTitle(repoList.get(0).getOwner());
        RepoAdapter repoAdapter = new RepoAdapter(repoList, this::startRepoInfoFragment);
        repo_list.setAdapter(repoAdapter);
        repoListVH.onShowRepoList();
    }

    @Override
    public void startRepoInfoFragment(Repository repository) {
        activityCallback.startRepoInfoFragment(repository);
        option_change.setVisible(false);
    }

    @Override
    public void onDownloadError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        repoListVH.setProgressVisible(false);
        repoListVH.onError();
    }

    @Override
    public void onCashError(Throwable error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getUserName() {
        return login_field.getText().toString();
    }

    @Override
    public void showLoading(boolean show) {
        repoListVH.setProgressVisible(show);
    }

    @Override
    protected BasePresenter getPresenter() {
        return repoListPresenter;
    }
}
