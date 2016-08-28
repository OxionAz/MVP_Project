package com.example.oxionaz.mvpproject.view.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.oxionaz.mvpproject.EventBus;
import com.example.oxionaz.mvpproject.model.db.models.Info;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.view.ui.adapters.RepoAdapter;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.toolbar_menu)
public class MainActivity extends AppCompatActivity implements
        com.example.oxionaz.mvpproject.view.View, EventBus {

    private RepoListPresenter repoListPresenter = new RepoListPresenter(this, this);

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

    @AfterViews
    void ready(){
        repo_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repo_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        repoListPresenter.tryGetDataFromDB();
        return super.onCreateOptionsMenu(menu);
    }

    @OptionsItem
    void option_change(){
        repoListPresenter.clearData();
        option_change.setVisible(false);
        info_text.setVisibility(View.VISIBLE);
        login_field.setVisibility(View.VISIBLE);
        confirm_button.setVisibility(View.VISIBLE);
        repo_list.setVisibility(View.GONE);
    }

    @Click
    void confirm_button(){
        repoListPresenter.onSearchClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (repoListPresenter != null) {
            repoListPresenter.onStop();
        }
    }

    @Override
    public void showList(List<Info> repoList) {
        if (option_change != null)
        option_change.setVisible(true);
        error_text.setVisibility(View.GONE);
        info_text.setVisibility(View.GONE);
        login_field.setVisibility(View.GONE);
        confirm_button.setVisibility(View.GONE);
        repo_list.setVisibility(View.VISIBLE);
        RepoAdapter repoAdapter = new RepoAdapter(repoList);
        repo_list.setAdapter(repoAdapter);
    }

    @Override
    public String getUserName() {
        return login_field.getText().toString();
    }

    @Override
    public void onDownloadError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        info_text.setVisibility(View.GONE);
        error_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDBError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}