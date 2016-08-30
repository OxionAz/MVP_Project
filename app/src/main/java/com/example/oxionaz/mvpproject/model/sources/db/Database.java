package com.example.oxionaz.mvpproject.model.sources.db;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;

import java.util.List;
import rx.Observable;

public interface Database {

    void saveRepositories(List<Repository> repositories);
    void saveBranches(List<Branch> branches);
    void saveContributors(List<Contributor> contributors);

    Observable<List<Repository>> getRepositories();
    Observable<List<Branch>> getBranches();
    Observable<List<Contributor>> getContributors();

    void clearRepositoryTable();
    void clearBranchTable();
    void clearContributorTable();

}
