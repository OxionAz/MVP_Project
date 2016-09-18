package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.presenter.RepoInfoPresenter;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import dagger.Module;
import dagger.Provides;
import static org.mockito.Mockito.mock;

@Module
public class ViewTestModule {

    @Provides
    RepoListPresenter provideRepoListPresenter() {
        return mock(RepoListPresenter.class);
    }

    @Provides
    RepoInfoPresenter provideRepoInfoPresenter() {
        return mock(RepoInfoPresenter.class);
    }
}
