package com.example.oxionaz.mvpproject.model.sources;

import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.util.CheckUtils;

public class MapperDTO {

    public static Repository mapRepo(RepositoryDTO object){
        if (CheckUtils.checkItem(object)) {
            Repository item = new Repository();
            item.setId(object.getId());
            item.setName(object.getName());
            item.setOwner(object.getOwnerDTO().getLogin());
            item.setLanguage(object.getLanguage());
            item.setHtml_url(object.getHtmlUrl());
            item.setDescription(object.getDescription());
            return item;
        } else return null;
    }

    public static Branch mapBranch(BranchDTO object){
        if (CheckUtils.checkItem(object)) {
            Branch item = new Branch();
            item.setSha(object.getCommitDTO().getSha());
            item.setName(object.getName());
            item.setUrl(object.getCommitDTO().getUrl());
            return item;
        } else return null;
    }

    public static Contributor mapContrib(ContributorDTO object){
        if (CheckUtils.checkItem(object)) {
            Contributor item = new Contributor();
            item.setId(object.getId());
            item.setLogin(object.getLogin());
            item.setAvatar_url(object.getAvatarUrl());
            item.setHtml_url(object.getHtmlUrl());
            return item;
        } else return null;
    }
}
