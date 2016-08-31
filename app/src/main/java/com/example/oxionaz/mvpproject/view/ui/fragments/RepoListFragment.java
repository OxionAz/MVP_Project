package com.example.oxionaz.mvpproject.view.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.view.ui.activities.ActivityCallback;
import com.example.oxionaz.mvpproject.view.ui.adapters.RepoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.fragment_repo_list)
@OptionsMenu(R.menu.toolbar_menu)
public class RepoListFragment extends BaseFragment implements RepoListFragmentView, EventBus {

    private RepoListPresenter repoListPresenter = new RepoListPresenter(this, this);
    private ActivityCallback activityCallback;

    @ViewById
    RecyclerView repo_list;

    @ViewById
    EditText login_field;

    @ViewById
    Button confirm_button;

    @ViewById
    TextView error_text, info_text;

    @OptionsMenuItem
    MenuItem option_change;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            activityCallback = (ActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement activityCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        repoListPresenter.onCreate(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        repoListPresenter.onSaveInstanceState(outState);
    }

    @AfterViews
    void ready(){
        repo_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repo_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        repoListPresenter.getRepoFromCash();
    }

    @OptionsItem
    void option_change(){
        repoListPresenter.clearRepoCash();
        option_change.setVisible(false);
        info_text.setVisibility(View.VISIBLE);
        login_field.setVisibility(View.VISIBLE);
        confirm_button.setVisibility(View.VISIBLE);
        repo_list.setVisibility(View.GONE);
        getActivity().setTitle(this.getString(R.string.app_name));
    }

    @Click
    void confirm_button(){
        repoListPresenter.onSearchClick();
    }

    @Override
    public void showRepoList(List<Repository> repoList) {
        if (option_change != null)
            option_change.setVisible(true);
        error_text.setVisibility(View.GONE);
        info_text.setVisibility(View.GONE);
        login_field.setVisibility(View.GONE);
        confirm_button.setVisibility(View.GONE);
        repo_list.setVisibility(View.VISIBLE);
        RepoAdapter repoAdapter = new RepoAdapter(repoList, this::startRepoInfoFragment);
        repo_list.setAdapter(repoAdapter);
        getActivity().setTitle(repoList.get(0).getOwner());
    }

    @Override
    public void startRepoInfoFragment(Repository repository) {
        activityCallback.startRepoInfoFragment(repository);
        option_change.setVisible(false);
    }

    @Override
    public String getUserName() {
        return login_field.getText().toString();
    }

    @Override
    public void onDownloadError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        info_text.setVisibility(View.GONE);
        error_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCashError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    public void makeSnackbar(int position){
        Snackbar.make(repo_list, String.valueOf(position), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return repoListPresenter;
    }
}
