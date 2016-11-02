package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import javax.inject.Inject;

@EFragment(R.layout.fragment_repo_list)
@OptionsMenu(R.menu.toolbar_menu)
public class RepoListFragment extends BaseFragment implements RepoListFragmentView, EventBus {

    private ActivityCallback activityCallback;
    private RepoListVH repoListVH;

    @Inject
    RepoListPresenter repoListPresenter;

    @ViewById
    RecyclerView repo_list;

    @ViewById
    RelativeLayout progress_bar;

    @ViewById
    EditText login_field;

    @ViewById
    Button confirm_button;

    @ViewById
    TextView error_text, info_text;

    @ViewById
    ImageView gh_icon;

    @OptionsMenuItem
    MenuItem option_change;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        repoListVH = new RepoListVH(getActivity(), repo_list, progress_bar, login_field,
                confirm_button, error_text, info_text, option_change, gh_icon);
        activityCallback = (ActivityCallback) getActivity();
        repoListPresenter.onCreate(this, this);
        repoListPresenter.getRepoFromCash();
    }

    @OptionsItem
    void option_change(){
        getActivity().setTitle(this.getString(R.string.app_name));
        repoListVH.onChangeClick();
        repoListPresenter.clearRepoCash();
    }

    @Click
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
