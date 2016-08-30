package com.example.oxionaz.mvpproject.model;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;

import java.util.List;
import rx.Observable;

public interface Model {

    Observable<List<Repository>> downloadRepositories(String owner);
    Observable<List<Branch>> downloadBranches(String owner, String repo);
    Observable<List<Contributor>> downloadContributors(String owner, String repo);

    Observable<List<Repository>> getRepositoriesFromCash();
    Observable<List<Branch>> getBranchesFromCash();
    Observable<List<Contributor>> getContributorsFromCash();

    void clearRepositoryCash();
    void clearBranchesCash();
    void clearContributorsCash();

}
