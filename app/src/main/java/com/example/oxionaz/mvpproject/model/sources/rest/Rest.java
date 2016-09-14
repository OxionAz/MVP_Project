package com.example.oxionaz.mvpproject.model.sources.rest;

import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import java.util.List;
import rx.Observable;

public interface Rest {
    Observable<List<RepositoryDTO>> downloadRepositoryList(String owner);
    Observable<List<BranchDTO>> downloadBranchList(String owner, String repo);
    Observable<List<ContributorDTO>> downloadContributorList(String owner, String repo);
}
