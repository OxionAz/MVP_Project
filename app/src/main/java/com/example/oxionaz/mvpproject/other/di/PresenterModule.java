package com.example.oxionaz.mvpproject.other.di;

import com.example.oxionaz.mvpproject.model.DataManager;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return new DataManager();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
