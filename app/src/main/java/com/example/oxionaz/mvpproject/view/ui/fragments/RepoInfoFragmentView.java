package com.example.oxionaz.mvpproject.view.ui.fragments;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.view.View;
import java.util.List;

public interface RepoInfoFragmentView extends View {

    void showContributors(List<Contributor> contributors);
    void showBranches(List<Branch> branches);

}
