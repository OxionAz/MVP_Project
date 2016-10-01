package com.example.oxionaz.mvpproject.view.ui.fragments.vh;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.oxionaz.mvpproject.R;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.view.ui.adapters.RepoAdapter;

import java.util.List;

public class RepoListVH extends ViewHelper {

    private RecyclerView repo_list;
    private ProgressBar progress_bar;
    private EditText login_field;
    private Button confirm_button;
    private TextView error_text, info_text;
    private MenuItem option_change;

    public RepoListVH(Context context, RecyclerView repo_list, ProgressBar progress_bar, EditText login_field, Button confirm_button, TextView error_text, TextView info_text, MenuItem option_change) {
        super(context);
        this.repo_list = repo_list;
        this.progress_bar = progress_bar;
        this.login_field = login_field;
        this.confirm_button = confirm_button;
        this.error_text = error_text;
        this.info_text = info_text;
        this.option_change = option_change;
        ready();
    }

    private void ready(){
        repo_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        repo_list.setLayoutManager(linearLayoutManager);
    }

    public void setProgressVisible(boolean show){
        if(show){
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }

    public void onChangeClick(){
        option_change.setVisible(false);
        info_text.setVisibility(View.VISIBLE);
        error_text.setVisibility(View.GONE);
        login_field.setVisibility(View.VISIBLE);
        confirm_button.setVisibility(View.VISIBLE);
        repo_list.setVisibility(View.GONE);
    }

    public void onSearchCLick(){
        if (option_change != null)
            option_change.setVisible(false);
        error_text.setVisibility(View.GONE);
        info_text.setVisibility(View.GONE);
        login_field.setVisibility(View.GONE);
        confirm_button.setVisibility(View.GONE);
    }

    public void onShowRepoList(){
        if (option_change != null)
            option_change.setVisible(true);
        error_text.setVisibility(View.GONE);
        info_text.setVisibility(View.GONE);
        login_field.setVisibility(View.GONE);
        confirm_button.setVisibility(View.GONE);
        repo_list.setVisibility(View.VISIBLE);
    }

    public void onError(){
        error_text.setVisibility(View.VISIBLE);
        login_field.setVisibility(View.VISIBLE);
        confirm_button.setVisibility(View.VISIBLE);
    }
}
