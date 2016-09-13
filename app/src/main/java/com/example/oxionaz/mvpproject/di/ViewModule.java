package com.example.oxionaz.mvpproject.di;

import com.example.oxionaz.mvpproject.presenter.RepoInfoPresenter;
import com.example.oxionaz.mvpproject.presenter.RepoListPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    @Provides
    RepoListPresenter provideRepoListPresenter() {
        return new RepoListPresenter();
    }

    @Provides
    RepoInfoPresenter provideRepoInfoPresenter() {
        return new RepoInfoPresenter();
    }
}
