package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.models.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.RepositoryDTO;
import java.util.List;
import rx.Observable;

public interface Rest {
    Observable<List<RepositoryDTO>> downloadRepositoryList(String owner);
    Observable<List<BranchDTO>> downloadBranchList(String owner, String repo);
    Observable<List<ContributorDTO>> downloadContributorList(String owner, String repo);
}
