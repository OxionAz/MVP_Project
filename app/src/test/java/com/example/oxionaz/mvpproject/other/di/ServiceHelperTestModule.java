package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.sources.rest.api.GitHubAPI;
import com.example.oxionaz.mvpproject.other.Const;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import static org.mockito.Mockito.mock;

@Module
public class ServiceHelperTestModule {

    @Provides
    @Singleton
    GitHubAPI provideApiInterface() {
        return mock(GitHubAPI.class);
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.immediate();
    }
}
