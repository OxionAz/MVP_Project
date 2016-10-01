package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.DataManagerTest;
import com.example.oxionaz.mvpproject.model.sources.rest.GitHubApiTest;
import com.example.oxionaz.mvpproject.model.sources.rest.RestServiceTest;
import com.example.oxionaz.mvpproject.other.BaseTest;
import com.example.oxionaz.mvpproject.presenter.RepoInfoPresenterTest;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenterTest;
import com.example.oxionaz.mvpproject.view.RepoInfoFragmentTest;
import com.example.oxionaz.mvpproject.view.RepoListFragmentTest;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {DataTestModule.class, ServiceHelperTestModule.class, ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class})
public interface TestComponent extends AppComponent {

    void inject(BaseTest baseTest);

    void inject(GitHubApiTest gitHubApiTest);

    void inject(RestServiceTest restServiceTest);

    void inject(DataManagerTest dataManagerTest);

    void inject(RepoListPresenterTest repoListPresenterTest);

    void inject(RepoInfoPresenterTest repoInfoPresenterTest);

    void inject(RepoListFragmentTest repoListFragmentTest);

    void inject(RepoInfoFragmentTest repoInfoFragmentTest);
}
