package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.DataManager;
import com.example.oxionaz.mvpproject.model.sources.ServiceHelper;
import com.example.oxionaz.mvpproject.model.sources.rest.RestService;
import com.example.oxionaz.mvpproject.presenter.BasePresenter;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoInfoFragment;
import com.example.oxionaz.mvpproject.view.ui.fragments.RepoListFragment;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ServiceHelperModule.class, ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(ServiceHelper serviceHelper);

    void inject(RestService restService);

    void inject(DataManager dataManager);

    void inject(BasePresenter basePresenter);

    void inject(RepoListFragment repoListFragment);

    void inject(RepoInfoFragment repoInfoFragment);
}
