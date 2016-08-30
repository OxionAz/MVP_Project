package com.example.oxionaz.mvpproject.view;

import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;

import java.util.List;

public interface View {
    void showList(List<Repository> RepoList);
    String getUserName();
}
