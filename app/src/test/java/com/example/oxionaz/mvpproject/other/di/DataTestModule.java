package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.sources.MapperDTO;
import com.example.oxionaz.mvpproject.model.sources.db.models.Branch;
import com.example.oxionaz.mvpproject.model.sources.db.models.Contributor;
import com.example.oxionaz.mvpproject.model.sources.db.models.Repository;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.BranchDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.ContributorDTO;
import com.example.oxionaz.mvpproject.model.sources.rest.dto.RepositoryDTO;
import com.example.oxionaz.mvpproject.other.EventBus;
import com.example.oxionaz.mvpproject.other.util.GsonUtils;
import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import rx.Observable;
import static org.mockito.Mockito.mock;

@Module
public class DataTestModule {

    private GsonUtils gsonUtils;

    public DataTestModule() {
        gsonUtils = new GsonUtils();
    }

    @Provides
    @Singleton
    GsonUtils provideTestUtils() {
        return gsonUtils;
    }

    @Provides
    @Singleton
    EventBus provideEventBus(){
        return mock(EventBus.class);
    }

    @Provides
    @Singleton
    List<RepositoryDTO> provideRepositoryDTOList() {
        RepositoryDTO[] repositoryDTOArray = gsonUtils.getGson()
                .fromJson(gsonUtils.readString("json/repos.json"), RepositoryDTO[].class);
        return Arrays.asList(repositoryDTOArray);
    }

    @Provides
    @Singleton
    List<BranchDTO> provideBranchDTOList() {
        BranchDTO[] branchDTOArray = gsonUtils.getGson()
                .fromJson(gsonUtils.readString("json/branches.json"), BranchDTO[].class);
        return Arrays.asList(branchDTOArray);
    }

    @Provides
    @Singleton
    List<ContributorDTO> provideContributorDTOList() {
        ContributorDTO[] contributorDTOArray = gsonUtils.getGson()
                .fromJson(gsonUtils.readString("json/contributors.json"), ContributorDTO[].class);
        return Arrays.asList(contributorDTOArray);
    }

    @Provides
    @Singleton
    List<Repository> provideRepositoryList(List<RepositoryDTO> repositoryDTOList){
        return Observable.from(repositoryDTOList)
                .map(MapperDTO::mapRepo)
                .toList()
                .toBlocking()
                .first();
    }

    @Provides
    @Singleton
    List<Branch> provideBranchList(List<BranchDTO> branchDTOList){
        return Observable.from(branchDTOList)
                .map(MapperDTO::mapBranch)
                .toList()
                .toBlocking()
                .first();
    }

    @Provides
    @Singleton
    List<Contributor> provideContributorList(List<ContributorDTO> contributorDTOList){
        return Observable.from(contributorDTOList)
                .map(MapperDTO::mapContrib)
                .toList()
                .toBlocking()
                .first();
    }
}
