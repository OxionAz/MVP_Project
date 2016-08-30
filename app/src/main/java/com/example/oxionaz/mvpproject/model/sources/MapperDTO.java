package com.example.oxionaz.mvpproject.model.sources;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.models.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.models.RepositoryDTO;

public class MapperDTO {

    public static Repository mapRepo(RepositoryDTO object){
        Repository item = new Repository();
        item.setId(object.getId());
        item.setName(object.getName());
        item.setOwner(object.getOwnerDTO().getLogin());
        item.setLanguage(object.getLanguage());
        item.setHtml_url(object.getHtmlUrl());
        return item;
    }

    public static Branch mapBranch(BranchDTO object){
        Branch item = new Branch();
        item.setSha(object.getCommitDTO().getSha());
        item.setName(object.getName());
        item.setUrl(object.getCommitDTO().getUrl());
        return item;
    }

    public static Contributor mapContrib(ContributorDTO object){
        Contributor item = new Contributor();
        item.setId(object.getId());
        item.setLogin(object.getLogin());
        item.setAvatar_url(object.getAvatarUrl());
        item.setHtml_url(object.getHtmlUrl());
        return item;
    }

}
