package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.sources.rest.RestClient;
import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.other.Const;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ServiceHelperModule {

    @Provides
    @Singleton
    GitHubAPI provideRestClient(){
        return new RestClient(Const.BASE_URL).getGitHubAPI();
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }
}
