package com.example.oxionaz.mvpproject.view.ui.fragments;

import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.view.View;

import java.util.List;

public interface RepoListFragmentView extends View {

    void showRepoList(List<Repository> repoList);
    void startRepoInfoFragment(Repository repo);
    String getUserName();
}
